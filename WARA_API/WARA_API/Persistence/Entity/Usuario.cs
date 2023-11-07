using System.ComponentModel.DataAnnotations;

namespace WARA_API.Persistence.Entity
{
    public class Usuario
    {
        [Key]
        public int Id { get; set; }
        public string Nombre { get; set; }
        public string Apellido { get; set; }
        public string User { get; set; }
        public string Password { get; set; }
        public string Role { get; set; }
    }
}
