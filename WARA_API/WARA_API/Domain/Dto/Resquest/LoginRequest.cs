using System.ComponentModel.DataAnnotations;

namespace WARA_API.Domain.Dto.Resquest
{
    public class LoginRequest
    {
        [Required(ErrorMessage = "El usuario es obligatorio")]
        public string Username { get; set; }
        [Required(ErrorMessage = "El password es obligatorio")]
        public string Password { get; set; }
    }
}
