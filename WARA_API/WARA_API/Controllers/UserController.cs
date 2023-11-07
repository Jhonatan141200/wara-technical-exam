using Microsoft.AspNetCore.Mvc;
using WARA_API.Domain;
using WARA_API.Domain.Dto.Response;
using WARA_API.Domain.Dto.Resquest;
using WARA_API.Domain.Repository;
namespace WARA_API.Controllers
{
    [ApiController]
    [Route("api/user")]
    public class UserController : ControllerBase
    {
        private readonly IUserRepository _ctRepo;
        public UserController(IUserRepository ctRepo)
        {
            _ctRepo = ctRepo;
        }
       

        [HttpPost("login")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        public async Task<LoginResponse> CrearCategoria([FromBody] LoginRequest loginRequest)
        {
            LoginResponse loginResponse = new LoginResponse();
            try
            {
                

                if (!ModelState.IsValid || loginRequest == null)
                {
                    loginResponse.Msg = ModelState.ToString() ?? "Bad Request";
                    loginResponse.Status = 400;
                    return loginResponse;
                }

                User user = new User();
                user.Username = loginRequest.Username;
                user.Password = loginRequest.Password;


                string rpta = await _ctRepo.LoginUser(user);

                switch (rpta)
                {
                    case "Inicio de sesión exitoso":
                        {
                            User userLogged = _ctRepo.GetUserByCredentials(user.Username, user.Password);
                            loginResponse.Msg = "Credenciales Correctas";
                            loginResponse.Status = 200;
                            loginResponse.Data = userLogged;
                            return loginResponse;
                        }

                    case "Contraseña incorrecta":
                        {
                            loginResponse.Msg = "Contraseña incorrecta";
                            loginResponse.Status = 401;
                            return loginResponse;
                        }
                    case "Usuario no encontrado":
                        {
                            loginResponse.Msg = "Usuario no encontrado";
                            loginResponse.Status = 401;
                            return loginResponse;
                        }
                    default:
                        {
                            loginResponse.Msg = "Bad Request";
                            loginResponse.Status = 400;
                            return loginResponse;
                        };
                }

            }
            catch (Exception ex)
            {
                loginResponse.Msg = ex.Message;
                loginResponse.Status = 500;
                return loginResponse;
            }
            
        }


    }
}
