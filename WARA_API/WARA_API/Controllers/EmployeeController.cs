using Microsoft.AspNetCore.Mvc;
using WARA_API.Domain;
using WARA_API.Domain.Dto.Response;
using WARA_API.Domain.Dto.Resquest;
using WARA_API.Domain.Repository;

namespace WARA_API.Controllers
{
    [ApiController]
    [Route("api/employee")]
    public class EmployeeController : ControllerBase
    {
        private readonly IEmployeeRepository _ctRepo;

        public EmployeeController(IEmployeeRepository ctRepo)
        {
            _ctRepo = ctRepo;
        }

        
        [HttpGet("all")]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        public EmployeeResponse GetEmployees()
        {   
            EmployeeResponse rpta = new EmployeeResponse();
            try
            {
                List<Employee> lista = _ctRepo.GetAll();
                if (lista == null)
                {
                    rpta.Msg = "Bad Request";
                    rpta.Status = StatusCodes.Status400BadRequest;
                    return rpta;
                }
                rpta.Msg = "Succees";
                rpta.Status = StatusCodes.Status200OK;
                rpta.Data = lista;
                return rpta;

            }
            catch (Exception ex)
            {
                rpta.Msg = ex.Message;
                rpta.Status = StatusCodes.Status500InternalServerError;
                return rpta;

            }
        }

        [HttpPost("save")]
        [ProducesResponseType(201, Type = typeof(EmployeeRequest))]
        [ProducesResponseType(StatusCodes.Status201Created)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        public EmployeeConfirmResponse SaveEmployee([FromBody] EmployeeRequest employeeRequest)
        {
            EmployeeConfirmResponse rpta = new EmployeeConfirmResponse();
            try
            {
                if (!ModelState.IsValid || employeeRequest == null)
                {
                    rpta.Msg = ModelState.ToString() ?? "Bad Request";
                    rpta.Status = StatusCodes.Status400BadRequest;
                    return rpta;
                }
                if (_ctRepo.ExistEmployee(employeeRequest.Dni))
                {
                    rpta.Msg = "El dni " + employeeRequest.Dni + "  ya existe";
                    rpta.Status = StatusCodes.Status400BadRequest;
                    return rpta;
                }
                Employee employee = new Employee();
                employee.Firstname = employeeRequest.Firstname;
                employee.Lastname = employeeRequest.Lastname;
                employee.Dni = employeeRequest.Dni;
                employee.Age = employeeRequest.Age;

                if (!_ctRepo.Save(employee))
                {
                    rpta.Msg = "Algo salió mal guardando el registro";
                    rpta.Status = StatusCodes.Status500InternalServerError;
                    return rpta;
                }
                rpta.Msg = "Emplead@ " + employee.Firstname + " ingresado con éxito";
                rpta.Status = StatusCodes.Status201Created;
                return rpta;

            }
            catch (Exception ex)
            {
                rpta.Msg = ex.Message;
                rpta.Status = StatusCodes.Status500InternalServerError;
                return rpta;
            }
            
        }


        [HttpPatch("update")]
        [ProducesResponseType(201, Type = typeof(EmployeeRequest))]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public EmployeeConfirmResponse UpdateEmployee([FromBody] EmployeeRequest employeeRequest)
        {
            EmployeeConfirmResponse rpta = new EmployeeConfirmResponse();
            try
            {
                if (!ModelState.IsValid || employeeRequest == null)
                {
                    rpta.Msg = ModelState.ToString() ?? "Bad Request";
                    rpta.Status = StatusCodes.Status400BadRequest;
                    return rpta;
                }
                if (!_ctRepo.ExistEmployee(employeeRequest.Id))
                {
                    rpta.Msg = "No existe empleado con el Id: " + employeeRequest.Id;
                    rpta.Status = StatusCodes.Status404NotFound;
                    return rpta;
                }
                if (_ctRepo.ExistEmployee(employeeRequest.Dni))
                {
                    rpta.Msg = "El dni " + employeeRequest .Dni + "  ya existe";
                    rpta.Status = StatusCodes.Status400BadRequest;
                    return rpta;
                }
                Employee employee = new Employee();
                employee.Id = employeeRequest.Id;
                employee.Firstname = employeeRequest.Firstname;
                employee.Lastname = employeeRequest.Lastname;
                employee.Dni = employeeRequest.Dni;
                employee.Age = employeeRequest.Age;

                if (!_ctRepo.Update(employee))
                {
                    rpta.Msg = "Algo salió mal actualizando el registro";
                    rpta.Status = StatusCodes.Status500InternalServerError;
                    return rpta;
                }
                rpta.Msg = "Emplead@ " + employee.Firstname + " actualizado con éxito";
                rpta.Status = StatusCodes.Status201Created;
                return rpta;

            }
            catch (Exception ex)
            {
                rpta.Msg = ex.Message;
                rpta.Status = StatusCodes.Status500InternalServerError;
                return rpta;
            }
            
        }


        [HttpDelete("delete/{employeeId:int}", Name = "BorrarCategoria")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<EmployeeConfirmResponse> DeleteEmployee(int employeeId)
        {
            EmployeeConfirmResponse rpta = new EmployeeConfirmResponse();
            try
            {   
                String resp = await _ctRepo.Delete(employeeId);
                switch (resp)
                {
                    case "Usuario eliminado con éxito":
                        {
                            rpta.Msg = "Usuario eliminado con éxito";
                            rpta.Status = StatusCodes.Status204NoContent;
                            return rpta;
                        }

                    case "No existe un usuario con el ID proporcionado":
                        {
                            rpta.Msg = "No existe un usuario con el ID proporcionado";
                            rpta.Status = StatusCodes.Status400BadRequest;
                            return rpta;
                        }
                    default:
                        {
                            rpta.Msg = "Algo salió mal eliminando el registro";
                            rpta.Status = StatusCodes.Status500InternalServerError;
                            return rpta;
                        };
                }

            }
            catch (Exception ex)
            {
                rpta.Msg = ex.Message;
                rpta.Status = StatusCodes.Status500InternalServerError;
                return rpta;
            }
        }


    }
}
