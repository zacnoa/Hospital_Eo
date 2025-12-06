package entity;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class UnderagePatient extends Patient{
    private Visitor legalGuardian;

   @Override
   public void basicInformation()
   {
       System.out.println("Maloljetni pacijent: "+ this.getName()+ " OIB: "+ this.getOIB() + " Skrbnik: " + this.getLegalGuardian().getName());
   }


    private UnderagePatient(UnderagePatientBuilder builder)
    {
        super(builder);
        this.legalGuardian=builder.legalGuardian;
    }

    public Visitor getLegalGuardian()
    {
        return legalGuardian;
    }
    public void setLegalGuardian(Visitor legalGuardian)
    {
        this.legalGuardian=legalGuardian;
    }

    public static Visitor generateLegalGuardian(Scanner sc)
    {
        Map<String,String> map=Person.getBasicInfo(sc);
        return new  Visitor(map.get("ime"),map.get("oib"));
    }

    public static  UnderagePatient generateUnderagePatient(Scanner sc)
    {
        Map<String,String> map=Patient.getBasicInfo(sc);
        return new UnderagePatientBuilder(map.get("ime"),map.get("oib"),map.get("dijagnoza"),PatientStatus.HOSPITALIZED)
                .legalGuardian(generateLegalGuardian(sc)).build();

    }


    public static class UnderagePatientBuilder extends PatientBuilder<UnderagePatientBuilder>
    {
         Visitor legalGuardian;


        UnderagePatientBuilder(String name,String OIB,String diagnosis,PatientStatus status)
        {
            super(name,OIB,diagnosis,status);
        }

         UnderagePatientBuilder legalGuardian(Visitor legalGuardian)
         {
             this.legalGuardian=legalGuardian;
             return this;
         }

         @Override
        public UnderagePatient build()
         {
             return new UnderagePatient(this);
         }



    }

}

