namespace WARA_API.Domain.Repository
{
    public interface IEmployeeRepository
    {
        List<Employee> GetAll();
        bool Save(Employee employee);

        Employee GetEmployeeById(int employeeId);
        bool Update(Employee employee);
        Task<string> Delete(int employeeId);
        bool Guardar();
        bool ExistEmployee(string dni);
        bool ExistEmployee(int id);

    }
}
