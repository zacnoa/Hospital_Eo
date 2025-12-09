package entity;

import java.util.Map;
import java.util.Scanner;

import static entity.Utility.addLegalGuardian;

public class UnderagePatient extends Patient{
    private String legalGuardianId;

   @Override
   public void basicInformation()
   {
       System.out.println("Maloljetni pacijent: "+ this.getName()+ " OIB: "+ this.getOIB() + " Skrbnik: " + this.getLegalGuardian().getName());
   }


    private UnderagePatient(UnderagePatientBuilder builder)
    {
        super(builder);
        this.legalGuardianId =builder.legalGuardian;
    }
    public UnderagePatient(){}

    public Visitor getLegalGuardian()
    {
        return PersonnelStorage.findVisitor(legalGuardianId);
    }
    public void setLegalGuardian(String legalGuardian)
    {
        this.legalGuardianId =legalGuardian;
    }
    public  String getLegalGuardianId()
    {
        return legalGuardianId;
    }
    public void setLegalGuardianId(String legalGuardianId)
    {
        this.legalGuardianId =legalGuardianId;
    }



    public static  UnderagePatient generateUnderagePatient(Scanner sc)
    {
        Map<String,String> map=Patient.getBasicInfo(sc);
        return new UnderagePatientBuilder(map.get("ime"),map.get("oib"),map.get("dijagnoza"),PatientStatus.HOSPITALIZED)
                .legalGuardian(addLegalGuardian(sc).getId()).build();

    }


    public static class UnderagePatientBuilder extends PatientBuilder<UnderagePatientBuilder>
    {
         String legalGuardian;

        UnderagePatientBuilder(String name,String OIB,String diagnosis,PatientStatus status)
        {
            super(name,OIB,diagnosis,status);
        }

         UnderagePatientBuilder legalGuardian(String legalGuardianId)
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

