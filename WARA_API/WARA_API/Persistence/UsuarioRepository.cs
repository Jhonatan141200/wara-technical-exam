
using Microsoft.Data.SqlClient;
using Microsoft.EntityFrameworkCore;
using System.Data;
using AutoMapper;
using WARA_API.Domain;
using WARA_API.Domain.Repository;
using WARA_API.Persistence.Data;
using WARA_API.Persistence.Entity;

namespace WARA_API.Persistence
{   
    public class UsuarioRepository : IUserRepository
    {
        private readonly ApplicationDbContext _ConexionDB;
        private readonly IMapper _Mapper;


        public UsuarioRepository(ApplicationDbContext conexionDB, IMapper userMapper)
        {
            _ConexionDB = conexionDB;
            _Mapper = userMapper;
        }

        

        public async Task<string> LoginUser(User user)
        {
            
            Usuario usuario = _Mapper.Map<Usuario>(user);

            SqlConnection conexion = (SqlConnection)_ConexionDB.Database.GetDbConnection();
            conexion.Open();
            var cmd = new SqlCommand("VerificarLogin", conexion)
            {
                CommandType = CommandType.StoredProcedure
            };
            cmd.Parameters.Add(new SqlParameter("@p_email", usuario.User));
            cmd.Parameters.Add(new SqlParameter("@p_password", usuario.Password));

            // Put Token

            string? result = await cmd.ExecuteScalarAsync() as string;

            conexion.Close();

            return result ?? "";
            
        }

        public User GetUserByCredentials (string username, string password)
        {
            Usuario u =  _ConexionDB.Usuario.First(u => u.User == username && u.Password == password);
            return _Mapper.Map<User>(u);
            
        }

    }
    
}
