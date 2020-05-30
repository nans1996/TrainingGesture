using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.JsonPatch;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using TranslateGestureAPI.Model;

namespace TranslateGestureAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TranslateController : ControllerBase
    {
        // GET: api/Translate
        [HttpGet]
        public String Get()
        {
            return "values:[{val: \"value\"}]";
        }

        // GET: api/Translate/5
        [HttpGet("{id}", Name = "Get")]
        public string Get(int id)
        {
            return "value";
        }

        // POST: api/Translate
        [HttpPost]
        public String Post([FromBody] JObject img)
        {
            try
            {
                ImageDataRequest image = JsonConvert.DeserializeObject<ImageDataRequest>(img.ToString());
                byte[] b = (byte[])(Array)image.data;
                if (!image.auth)
                {
                    image.name = Guid.NewGuid().ToString();
                }
                return Translate.TranslateMethod(b, image.name, image.auth);
            }
           catch (Exception e)
            {
                return e.Message;
            }


        }

        // PUT: api/Translate/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] string value)
        {
        }

        // DELETE: api/ApiWithActions/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
