package entity;

import java.util.Map;
import java.util.Scanner;

import static entity.Utility.addLegalGuardian;

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
    public UnderagePatient(){}

    public Visitor getLegalGuardian()
    {
        return legalGuardian;
    }
    public void setLegalGuardian(Visitor legalGuardian)
    {
        this.legalGuardian =legalGuardian;
    }
    public  Visitor getLegalGuardianId()
    {
        return legalGuardian;
    }
    public void setLegalGuardianId(Visitor legalGuardianId)
    {
        this.legalGuardian =legalGuardianId;
    }



    public static  UnderagePatient generateUnderagePatient(Scanner sc)
    {
        Map<String,String> map=Patient.getBasicInfo(sc);
        return new UnderagePatientBuilder(map.get("ime"),map.get("oib"),map.get("dijagnoza"),PatientStatus.HOSPITALIZED)
                .legalGuardian(addLegalGuardian(sc)).build();

    }


    public static class UnderagePatientBuilder extends PatientBuilder<UnderagePatientBuilder>
    {
         Visitor legalGuardian;

         public UnderagePatientBuilder(String name,String OIB,String diagnosis,PatientStatus status)
        {
            super(name,OIB,diagnosis,status);
        }

         public UnderagePatientBuilder legalGuardian(Visitor legalGuardian)
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

