package entity;

import java.util.Map;
import java.util.Scanner;

public class ERPatient extends Patient {

    Integer priority;

    private ERPatient(ERPatientBuilder builder)
    {
        super(builder);
        this.priority=builder.priority;
    }

    public static Integer generatePriority(Scanner sc)
    {
        System.out.println("Unesite vaznost pacijenta");
        Integer priority=sc.nextInt();
        return priority;
    }

    static public ERPatient generateERPatient(Scanner sc)
    {
        Map<String,String> map= Patient.getBasicInfo(sc);


        return new ERPatientBuilder(map.get("name"),map.get("oib"),map.get("dijagnoza"),PatientStatus.HOSPITALIZED)
                .priority(ERPatient.generatePriority(sc))
                .build();
    }

    @Override
    public void basicInformation()
    {
        System.out.println("Hitni pacijent: " +this.getName() +" "+ this.getOIB() );
    }

    public static class ERPatientBuilder extends PatientBuilder<ERPatientBuilder>
    {
        Integer priority;

        public ERPatientBuilder(String name,String OIB,String diagnosis,PatientStatus status)
        {
            super(name,OIB,diagnosis,status);
        }

        public ERPatientBuilder priority(Integer priority)
        {
            this.priority=priority;
            return this;
        }

        @Override
        public ERPatient build()
        {
            return new ERPatient(this);
        }
    }

}
