using AutoMapper;
using Microsoft.Data.SqlClient;
using Microsoft.EntityFrameworkCore;
using System.Data;
using WARA_API.Domain;
using WARA_API.Domain.Repository;
using WARA_API.Persistence.Data;
using WARA_API.Persistence.Entity;

namespace WARA_API.Persistence
{
    public class TrabajadorRepository : IEmployeeRepository
    {
        private readonly ApplicationDbContext _ConexionDB;
        private readonly IMapper _Mapper;


        public TrabajadorRepository(ApplicationDbContext conexionDB, IMapper userMapper)
        {
            _ConexionDB = conexionDB;
            _Mapper = userMapper;
        }

        public async Task<string> Delete(int employeeId)
        {
            {
                SqlConnection conexion = (SqlConnection)_ConexionDB.Database.GetDbConnection();
                conexion.Open();
                var cmd = new SqlCommand("EliminarUsuarioPorID", conexion)
                {
                    CommandType = CommandType.StoredProcedure
                };
                cmd.Parameters.Add(new SqlParameter("@UserID", employeeId));

                // Put Token

                string? result = await cmd.ExecuteScalarAsync() as string;

                conexion.Close();

                return result ?? "";
            }
        }

        public List<Employee> GetAll()
        {
            List<Trabajador> lista = _ConexionDB.Trabajador.OrderBy(c => c.Nombre).ToList();
            List<Employee> result = new List<Employee>();
            foreach (var trabajador in lista)
            {   

                result.Add(_Mapper.Map<Employee>(trabajador));
            }
            return result;
        }

        public bool Save(Employee employee)
        {
            _ConexionDB.Trabajador.Add(_Mapper.Map<Trabajador>(employee));
            return Guardar();
        }

        public bool Update(Employee employee)
        {
            _ConexionDB.Trabajador.Update(_Mapper.Map<Trabajador>(employee));
            return Guardar();
        }
        public bool Guardar()
        {
            return _ConexionDB.SaveChanges() >= 0 ? true : false;
        }

        public bool ExistEmployee(string dni)
        {
            bool valor = _ConexionDB.Trabajador.Any(t => t.Dni.Trim() == dni.Trim());
            return valor;
        }
        public bool ExistEmployee(int id)
        {
            bool valor = _ConexionDB.Trabajador.Any(t => t.Id == id);
            return valor;
        }
        public Employee GetEmployeeById(int employeeId)
        {
            return _Mapper.Map<Employee>(_ConexionDB.Trabajador.First(c => c.Id == employeeId));
        }

    }
}
