namespace WARA_API.Domain.Dto.Response
{
    public class EmployeeResponse
    {
        public string Msg { get; set; }
        public int Status { get; set; }
        public List<Employee> Data { get; set; }
    }
}
