using Microsoft.ML;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using TranslateGestureAPI.Model;

namespace TranslateGestureAPI
{
   

    public class Translate
    {

        public static bool SavePhoto(byte[] byteImage, string path)
        {
            try
            {
                Image patternimage;
                //сохранение
                using (var stream = new MemoryStream(byteImage))
                {
                    patternimage = new Bitmap(stream);
                    patternimage.Save(path, ImageFormat.Png);
                }
                return true;
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception: " + e);
                return false;
            }

        }

        public static string TranslateMethod(byte[] byteImage, string token, bool auth)
        {
    
            try
            {
                var  context = new MLContext();
                var data = context.Data.LoadFromTextFile<ImageData>("./labels.csv", separatorChar: ',');

                var pipeline = context.Transforms.Conversion.MapValueToKey("LabelKey", "Label")
                    .Append(context.Transforms.LoadImages("input", "images", nameof(ImageData.ImagePath)))
                    .Append(context.Transforms.ResizeImages("input", InceptionSettings.ImageWidth, InceptionSettings.ImageHeight, "input"))
                    .Append(context.Transforms.ExtractPixels("input", interleavePixelColors: InceptionSettings.ChannelList, offsetImage: InceptionSettings.Main))
                    .Append(context.Model.LoadTensorFlowModel("./InceptionModel/tensorflow_inception_graph.pb")
                           .ScoreTensorFlowModel(new[] { "softmax2_pre_activation" }, new[] { "input" }, addBatchDimensionInput: true))
                    .Append(context.MulticlassClassification.Trainers.LbfgsMaximumEntropy("LabelKey", "softmax2_pre_activation"))
                    .Append(context.Transforms.Conversion.MapKeyToValue("PredictedLabelValue", "PredictedLabel"));

                var model = pipeline.Fit(data);
                IEnumerable<ImageData> imageData;
                if (!auth)
                {
                   imageData = File.ReadAllLines("./labels.csv")
                    .Select(l => l.Split(','))
                    .Select(l => new ImageData { ImagePath = Path.Combine(Environment.CurrentDirectory, "images", l[0]) });
                } else
                {
                    imageData = File.ReadAllLines("./images/userimage/" + token + "/" + token + ".csv")
                    .Select(l => l.Split(','))
                    .Select(l => new ImageData { ImagePath = Path.Combine(Environment.CurrentDirectory, "images", l[0]) });
                }
              

                var imageDataView = context.Data.LoadFromEnumerable(imageData);
                var predicate = model.Transform(imageDataView);
                var imagePredication = context.Data.CreateEnumerable<imagePrediction>(predicate, reuseRowObject: false, ignoreMissingColumns: true);

                var evalPrediction = model.Transform(data);
                var metrics = context.MulticlassClassification.Evaluate(evalPrediction, labelColumnName: "LabelKey", predictedLabelColumnName: "PredictedLabel");

                Console.WriteLine($"Log loss - {metrics.LogLoss}");

                //the end обучения?
                var predictionFunc = context.Model.CreatePredictionEngine<ImageData, imagePrediction>(model);

                imagePrediction singlePrediction = null;
                string pathPhoto = Path.Combine(Environment.CurrentDirectory, "images\\userimage\\" + token + ".jpg");

                if (SavePhoto(byteImage, pathPhoto))
                {
                   
                    singlePrediction = predictionFunc.Predict(new ImageData
                    {
                        ImagePath = Path.Combine(Environment.CurrentDirectory, "images\\userimage\\" + token + ".jpg")
                }) ;

                   // File.Delete(pathPhoto);
                }

                string answer = "Image was predicted as a " + singlePrediction.PredictedLabelValue +
                 " with a score of " + singlePrediction.Score.Max();

                Console.WriteLine(answer);

                return answer;
            }
            catch (Exception e)
            {
                return null;
            }
        }

    }
}
