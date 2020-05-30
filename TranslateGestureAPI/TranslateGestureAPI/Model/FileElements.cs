using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace TranslateGestureAPI.Model
{
    public class FileElements
    {
        public string path { get; set; }
        public string value { get; set; }

        public FileElements() { }

        public FileElements(string path, string value)
        {
            this.path = path;
            this.value = value;
        }
    }
}
