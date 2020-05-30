using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace TranslateGestureAPI.Model
{
    public class ImageDataTrainingRequest
    {
        public SByte[] data { get; set; }
        public string name { get; set; }
        public string value { get; set; }

    }
}
