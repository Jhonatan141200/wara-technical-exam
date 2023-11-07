using Microsoft.EntityFrameworkCore;
using WARA_API.Persistence.Entity;

namespace WARA_API.Persistence.Data
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options)
        {

        }

        //Agregar los modelos aquí
        public DbSet<Usuario> Usuario { get; set; }
        public DbSet<Trabajador> Trabajador { get; set; }

    }
}
