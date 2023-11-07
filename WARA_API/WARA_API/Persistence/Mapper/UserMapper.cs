using AutoMapper;
using WARA_API.Domain;
using WARA_API.Persistence.Entity;

namespace WARA_API.Persistence.Mapper
{
    public class UserMapper : Profile
    {
        public UserMapper()
        {
            CreateMap<Usuario, User>()
                .ForMember(user => user.Id, opt => opt.MapFrom(usuario => usuario.Id))
                .ForMember(user => user.Username, opt => opt.MapFrom(usuario => usuario.User))
                .ForMember(user => user.Password, opt => opt.MapFrom(usuario => usuario.Password))
                .ForMember(user => user.Firstname, opt => opt.MapFrom(usuario => usuario.Nombre))
                .ForMember(user => user.Lastname, opt => opt.MapFrom(usuario => usuario.Apellido))               
                .ReverseMap();
        }
    }
}
