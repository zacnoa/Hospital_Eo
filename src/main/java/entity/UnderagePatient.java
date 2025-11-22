package entity;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class UnderagePatient extends Patient{
    private Optional<Person> legalGuardian;

   @Override
   public void basicInformation()
   {
       System.out.println("Maloljetni pacijent: "+ this.getName()+ " OIB: "+ this.getOIB() + " Skrbnik: " + this.getLegalGuardian().get().getName());
   }


    private UnderagePatient(UnderagePatientBuilder builder)
    {
        super(builder);
        this.legalGuardian=builder.legalGuardian;
    }

//    public static Person generateLegalGuardian(Scanner sc)
//    {
//        Map<String,String> map=Person.getBasicInfo(sc);
//        return new
//    }
//
//    public  UnderagePatient generateUnderagePatient(Scanner sc)
//    {
//        Patient.getBasicInfo(sc);
//
//    }





    public Optional<Person> getLegalGuardian() {
        return legalGuardian;
    }

    public static class UnderagePatientBuilder extends PatientBuilder<UnderagePatientBuilder>
    {
         Optional<Person> legalGuardian;


        UnderagePatientBuilder(String name,String OIB,String diagnosis,PatientStatus status)
        {
            super(name,OIB,diagnosis,status);
        }

         UnderagePatientBuilder legalGuardian(Person legalGuardian)
         {
             this.legalGuardian=Optional.of(legalGuardian);
             return this;
         }

         @Override
        public UnderagePatient build()
         {
             return new UnderagePatient(this);
         }



    }

}

