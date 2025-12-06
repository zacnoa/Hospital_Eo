package adapters;

import entity.PersonnelStorage;
import entity.Room;
import jakarta.json.bind.adapter.JsonbAdapter;

public class RoomAdapter implements JsonbAdapter<Room, String> {

    @Override
    public String adaptToJson(Room obj)
    {
        return obj.getId();
    }
    @Override
    public Room adaptFromJson(String id)
    {
        return PersonnelStorage.roomStorage.get(id);
    }
}
