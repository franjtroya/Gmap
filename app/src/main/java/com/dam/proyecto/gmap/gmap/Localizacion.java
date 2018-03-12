package com.dam.proyecto.gmap.gmap;

/**
 * Created by dam on 21/02/2018.
 */

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.GregorianCalendar;

public class Localizacion implements Parcelable {

    private Location localizacion;
    private Date fecha;

    public Localizacion() {
        this(new Location("provider"));
    }

    public Localizacion(Location localizacion) {
        this(localizacion, new GregorianCalendar().getTime());
    }

    public Localizacion(Location localizacion, Date fecha) {
        this.localizacion = localizacion;
        this.fecha = fecha;
    }

    public Location getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(Location localizacion) {
        this.localizacion = localizacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Localizacion{" +
                "localizacion=" + localizacion.toString() +
                ", fecha=" + fecha.toString() +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.localizacion, flags);
        dest.writeLong(this.fecha != null ? this.fecha.getTime() : -1);
    }

    protected Localizacion(Parcel in) {
        this.localizacion = in.readParcelable(Location.class.getClassLoader());
        long tmpFecha = in.readLong();
        this.fecha = tmpFecha == -1 ? null : new Date(tmpFecha);
    }

    public static final Creator<Localizacion> CREATOR = new Creator<Localizacion>() {
        @Override
        public Localizacion createFromParcel(Parcel source) {
            return new Localizacion(source);
        }

        @Override
        public Localizacion[] newArray(int size) {
            return new Localizacion[size];
        }
    };
}
