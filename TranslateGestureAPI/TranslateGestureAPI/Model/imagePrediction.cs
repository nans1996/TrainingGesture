using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace TranslateGestureAPI.Model
{
    public class imagePrediction: ImageData
    { 
        public float[] Score { get; set; }
        public string PredictedLabelValue { get; set; }
    }
}
