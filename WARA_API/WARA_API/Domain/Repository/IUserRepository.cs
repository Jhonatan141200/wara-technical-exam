

namespace WARA_API.Domain.Repository
{
    public interface IUserRepository
    {
        Task<string> LoginUser(User user);
        User GetUserByCredentials(string username, string password);
    }
}
