
using eGradjevina.DATA.Models;
using eGradjevina.WEB.Data;
using eGradjevina.WEB.Models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.Mvc;

namespace ePhotographyEquipment.WEB.Controllers
{
    public class ProizvodiController : Controller
    {
        MojContext mc = new MojContext();
        // GET: Proizvodi
        public ActionResult Pretraga(string q,int tipProizvoda)
        {

            var model = mc.Proizvodi.Where(x => x.Naziv.Contains(q)).ToList();
            if (tipProizvoda != 0)
            {
                model=model.Where(x => x.TipProizvodaId == tipProizvoda).ToList();
            }


            return Json(model, JsonRequestBehavior.AllowGet);
        }



        public ActionResult InsertImage(int id, string path) {

            byte[] buff = null;
            FileStream fs = new FileStream(path,
                                           FileMode.Open,
                                           FileAccess.Read);
            BinaryReader br = new BinaryReader(fs);
            long numBytes = new FileInfo(path).Length;
            buff = br.ReadBytes((int)numBytes);


            Proizvod p = mc.Proizvodi.Find(id);
            if (p != null) {


                p.Slika = buff;
                mc.SaveChanges();
            }

            return null;
        }
    }
}