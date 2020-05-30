using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using TranslateGestureAPI.Model;

namespace TranslateGestureAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TrainingController : ControllerBase
    {
        // GET: api/Training
        [HttpGet]
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // POST: api/Training
        [HttpPost]
        public string Post([FromBody] JObject obj)
        {
            try
            {
            ImageDataTrainingRequest image = JsonConvert.DeserializeObject<ImageDataTrainingRequest>(obj.ToString());
            byte[] byteImage = (byte[])(Array)image.data;
            return Training.GenerateTranslateList(byteImage, image.name, image.value);
            } catch (Exception e)
            {
                return "Error of the training";
            }
            
        }

        // PUT: api/Training/5
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
