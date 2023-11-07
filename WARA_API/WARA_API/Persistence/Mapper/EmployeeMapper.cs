using AutoMapper;
using WARA_API.Domain;
using WARA_API.Persistence.Entity;

namespace WARA_API.Persistence.Mapper
{
    public class EmployeeMapper : Profile
    {
        public EmployeeMapper()
        {
            CreateMap<Trabajador, Employee>()
                .ForMember(employee => employee.Id, opt => opt.MapFrom(trabajador => trabajador.Id))
                .ForMember(employee => employee.Firstname, opt => opt.MapFrom(trabajador => trabajador.Nombre))
                .ForMember(employee => employee.Lastname, opt => opt.MapFrom(trabajador => trabajador.Apellido))
                .ForMember(employee => employee.Dni, opt => opt.MapFrom(trabajador => trabajador.Dni))
                .ForMember(employee => employee.Age, opt => opt.MapFrom(trabajador => trabajador.Edad))
                .ReverseMap();
        }
    }
}
