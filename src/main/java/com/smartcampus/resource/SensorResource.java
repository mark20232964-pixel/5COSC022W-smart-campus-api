package com.smartcampus.resource;

import com.smartcampus.model.Sensor;
import com.smartcampus.model.Room;
import com.smartcampus.repository.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {


    @POST
    public Response createSensor(Sensor sensor) {

        if (sensor.getId() == null || sensor.getId().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Sensor ID required")
                    .build();
        }

        if (sensor.getType() == null || sensor.getType().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Sensor type required")
                    .build();
        }

        Room room = DataStore.rooms.get(sensor.getRoomId());

        if (room == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Room does not exist")
                    .build();
        }

        DataStore.sensors.put(sensor.getId(), sensor);

        // 🔗 Link sensor to room
        room.getSensorIds().add(sensor.getId());

        return Response.status(Response.Status.CREATED)
                .entity(sensor)
                .build();
    }

    @GET
    public Collection<Sensor> getSensors(@QueryParam("type") String type) {

        // if no filter → return all
        if (type == null || type.isEmpty()) {
            return DataStore.sensors.values();
        }

        // filter by type
        java.util.List<Sensor> filtered = new java.util.ArrayList<>();

        for (Sensor sensor : DataStore.sensors.values()) {
            if (sensor.getType() != null &&
                    sensor.getType().equalsIgnoreCase(type)) {

                filtered.add(sensor);
            }
        }

        return filtered;
    }

    @PUT
    @Path("/{id}/reading")
    public Response updateReading(@PathParam("id") String id,
                                  java.util.Map<String, Double> body) {

        Sensor sensor = DataStore.sensors.get(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        Double value = body.get("currentValue");

        if (value == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("currentValue is required")
                    .build();
        }

        sensor.setCurrentValue(value);

        return Response.ok(sensor).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSensor(@PathParam("id") String id) {

        Sensor sensor = DataStore.sensors.remove(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }


        Room room = DataStore.rooms.get(sensor.getRoomId());
        if (room != null) {
            room.getSensorIds().remove(id);
        }

        return Response.ok("Sensor deleted successfully").build();
    }

    @Path("/{id}/readings")
    public SensorReadingResource getReadingResource(@PathParam("id") String id) {
        return new SensorReadingResource(id);
    }
}