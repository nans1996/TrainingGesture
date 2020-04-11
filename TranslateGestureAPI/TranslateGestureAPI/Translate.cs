using Microsoft.ML;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using TranslateGestureAPI.Model;

namespace TranslateGestureAPI
{
    public class Translate
    {
        
       public static string TranslateMethod()
        {
            var context = new MLContext();
            var data = context.Data.LoadFromTextFile<ImageData>("./labels.csv", separatorChar: ';');

            var pipeline = context.Transforms.Conversion.MapValueToKey("LabelKey", "Label")
                .Append(context.Transforms.LoadImages("input", "images", nameof(ImageData.ImagePath)))
                .Append(context.Transforms.ResizeImages("input", InceptionSettings.ImageWidth, InceptionSettings.ImageHeight, "input"))
                .Append(context.Transforms.ExtractPixels("input", interleavePixelColors: InceptionSettings.ChannelList, offsetImage: InceptionSettings.Main))
                .Append(context.Model.LoadTensorFlowModel("./InceptionModel/tensorflow_inception_graph.pb")
                       .ScoreTensorFlowModel(new[] { "softmax2_pre_activation" }, new[] { "input" }, addBatchDimensionInput: true))
                .Append(context.MulticlassClassification.Trainers.LbfgsMaximumEntropy("LabelKey", "softmax2_pre_activation"))
                .Append(context.Transforms.Conversion.MapKeyToValue("PredictedLabelValue", "PredictedLabel"));

            var model = pipeline.Fit(data);
            var imageData = File.ReadAllLines("./labels.csv")
                .Select(l => l.Split(';'))
                .Select(l => new ImageData { ImagePath = Path.Combine(Environment.CurrentDirectory, "images", l[0]) });

            var imageDataView = context.Data.LoadFromEnumerable(imageData);
            var predicate = model.Transform(imageDataView);
            var imagePredication = context.Data.CreateEnumerable<imagePrediction>(predicate, reuseRowObject: false, ignoreMissingColumns: true);

            var evalPrediction = model.Transform(data);
            var metrics = context.MulticlassClassification.Evaluate(evalPrediction, labelColumnName: "LabelKey", predictedLabelColumnName: "PredictedLabel");

            Console.WriteLine($"Log loss - {metrics.LogLoss}");

            //the end обучения?

            var predictionFunc = context.Model.CreatePredictionEngine<ImageData, imagePrediction>(model);
            var singlePrediction = predictionFunc.Predict(new ImageData
            {
                ImagePath = Path.Combine(Environment.CurrentDirectory, "images", "a2.jpg")
            });

            string answer = "Image " +Path.GetFileName(singlePrediction.ImagePath)+ " was predicted as a " + singlePrediction.PredictedLabelValue +
             " with a score of " + singlePrediction.Score.Max();

            Console.WriteLine(answer);

            return answer;
        }

    }
}
