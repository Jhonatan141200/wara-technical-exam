namespace WARA_API.Domain.Dto.Response
{
    public class LoginResponse
    {
        public string Msg { get; set; }
        public int Status { get; set; }

        public User Data { get; set; }
    }
}
