namespace eGradjevina.WEB.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class _migracija : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Korisniks",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Username = c.String(),
                        Password = c.String(),
                        Ime = c.String(),
                        IsDeleted = c.Boolean(nullable: false),
                        Prezime = c.String(),
                        Email = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Narudzbas",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        IsDeleted = c.Boolean(nullable: false),
                        DatumNarudzbe = c.DateTime(nullable: false),
                        BrojNarudzbe = c.String(),
                        UkupanIznos = c.Single(nullable: false),
                        KorisnikId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Korisniks", t => t.KorisnikId)
                .Index(t => t.KorisnikId);
            
            CreateTable(
                "dbo.Proizvods",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        IsDeleted = c.Boolean(nullable: false),
                        Naziv = c.String(),
                        Sifra = c.String(),
                        Opis = c.String(),
                        Slika = c.Binary(),
                        Cijena = c.Single(nullable: false),
                        TipProizvodaId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.TipProizvodas", t => t.TipProizvodaId)
                .Index(t => t.TipProizvodaId);
            
            CreateTable(
                "dbo.TipProizvodas",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Naziv = c.String(),
                        IsDeleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.StavkeNarudzbes",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        IsDeleted = c.Boolean(nullable: false),
                        ProizvodId = c.Int(nullable: false),
                        NaruzbaId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Narudzbas", t => t.NaruzbaId)
                .ForeignKey("dbo.Proizvods", t => t.ProizvodId)
                .Index(t => t.ProizvodId)
                .Index(t => t.NaruzbaId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.StavkeNarudzbes", "ProizvodId", "dbo.Proizvods");
            DropForeignKey("dbo.StavkeNarudzbes", "NaruzbaId", "dbo.Narudzbas");
            DropForeignKey("dbo.Proizvods", "TipProizvodaId", "dbo.TipProizvodas");
            DropForeignKey("dbo.Narudzbas", "KorisnikId", "dbo.Korisniks");
            DropIndex("dbo.StavkeNarudzbes", new[] { "NaruzbaId" });
            DropIndex("dbo.StavkeNarudzbes", new[] { "ProizvodId" });
            DropIndex("dbo.Proizvods", new[] { "TipProizvodaId" });
            DropIndex("dbo.Narudzbas", new[] { "KorisnikId" });
            DropTable("dbo.StavkeNarudzbes");
            DropTable("dbo.TipProizvodas");
            DropTable("dbo.Proizvods");
            DropTable("dbo.Narudzbas");
            DropTable("dbo.Korisniks");
        }
    }
}
