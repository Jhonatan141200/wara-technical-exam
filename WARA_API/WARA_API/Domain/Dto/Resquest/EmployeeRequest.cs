using System.ComponentModel.DataAnnotations;

namespace WARA_API.Domain.Dto.Resquest
{
    public class EmployeeRequest
    {   
        public int Id { get; set; }
        [Required(ErrorMessage = "El nombre es obligatorio")]
        public string Firstname { get; set; }
        [Required(ErrorMessage = "El apellido es obligatorio")]
        public string Lastname { get; set; }
        [Required(ErrorMessage = "El dni es obligatorio")]
        public string Dni { get; set; }
        [Required(ErrorMessage = "La edad es obligatorio")]
        public int Age { get; set; }

    }
}
