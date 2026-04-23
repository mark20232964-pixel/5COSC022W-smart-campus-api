package com.smartcampus.resource;

import com.smartcampus.model.Room;
import com.smartcampus.repository.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    @GET
    public Collection<Room> getRooms() {
        return DataStore.rooms.values();
    }

    @POST
    public Response createRoom(Room room) {

        if (room.getId() == null || room.getId().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Room ID required")
                    .build();
        }

        DataStore.rooms.put(room.getId(), room);

        return Response.status(Response.Status.CREATED)
                .entity(room)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id) {

        Room room = DataStore.rooms.get(id);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        //   check if room has sensors
        if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Cannot delete room with sensors")
                    .build();
        }

        DataStore.rooms.remove(id);

        return Response.ok("Room deleted successfully").build();
    }

    @PUT
    @Path("/{id}")
    public Response updateRoom(@PathParam("id") String id, Room updatedRoom) {

        Room existing = DataStore.rooms.get(id);

        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        if (updatedRoom.getName() != null) {
            existing.setName(updatedRoom.getName());
        }

        if (updatedRoom.getCapacity() > 0) {
            existing.setCapacity(updatedRoom.getCapacity());
        }

        return Response.ok(existing).build();
    }
}