using CsvHelper;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TranslateGestureAPI.Model;

namespace TranslateGestureAPI
{
    public class Training
    {
        public static void AddInfoCSV(string pathFile, string path, string value)
        {
            FileElements csvRecord = new FileElements(path, value);
            using (var writer = new StreamWriter(pathFile))
            using (var csv = new CsvWriter(writer, CultureInfo.InvariantCulture))
            {
                csv.WriteRecord(csvRecord);
            }
        }

        public static string GenerateTranslateList(byte[] byteImage, string token, string value)
        {
            try
            {
                //если директории нет, создаем
                string path = Path.Combine(Environment.CurrentDirectory, "images\\userimage\\" + token);
                Directory.CreateDirectory(path);
                //сохранение фото
                string pathDirectory = Path.Combine(path, value);
                Directory.CreateDirectory(pathDirectory);
                var countFile = new DirectoryInfo(pathDirectory).GetFiles().Length;
                string photoName = value + countFile + ".jpg";
                string pathPhoto = Path.Combine(pathDirectory, photoName);
                Translate.SavePhoto(byteImage, pathPhoto);
                //есть ли файл csv
                string pathFile = Path.Combine(path, token + ".csv");
                bool file = File.Exists(pathFile);
                string pathUser = "images\\userimage\\" + token + "\\" + value + "\\"+ photoName;
                if (!file)
                {
                    File.WriteAllText(pathFile, pathUser+","+value + Environment.NewLine);
                    //AddInfoCSV(pathFile, pathUser, value);
                }
                else
                {
                    // AddInfoCSV(pathFile, pathUser, value);
                    File.AppendAllText(pathFile, pathUser + "," + value+Environment.NewLine);
                }
                return "Successful";
            } catch (Exception e)
            {
                return "Error";
            }
         

        }
    }
}
