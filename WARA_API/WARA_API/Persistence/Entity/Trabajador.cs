﻿using System.ComponentModel.DataAnnotations;

namespace WARA_API.Persistence.Entity
{
    public class Trabajador
    {
        [Key]
        public int Id { get; set; }
        public string Nombre { get; set; }
        public string Apellido { get; set; }
        public string Dni { get; set; }
        public int Edad { get; set; }
    }
}
