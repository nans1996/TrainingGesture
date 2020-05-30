using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace TranslateGestureAPI.Model
{
    public class ImageDataRequest
    { 
        public SByte[] data { get; set; }
        public string name { get; set; }
        public bool auth { get; set; }

    }
}
