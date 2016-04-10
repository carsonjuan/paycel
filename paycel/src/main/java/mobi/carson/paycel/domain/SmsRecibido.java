package mobi.carson.paycel.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SmsRecibido.
 */
@Entity
@Table(name = "sms_recibido")
public class SmsRecibido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "numero_telefono")
    private String numeroTelefono;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "fecha_hora_recibido")
    private ZonedDateTime fechaHoraRecibido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ZonedDateTime getFechaHoraRecibido() {
        return fechaHoraRecibido;
    }

    public void setFechaHoraRecibido(ZonedDateTime fechaHoraRecibido) {
        this.fechaHoraRecibido = fechaHoraRecibido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SmsRecibido smsRecibido = (SmsRecibido) o;
        if(smsRecibido.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, smsRecibido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SmsRecibido{" +
            "id=" + id +
            ", numeroTelefono='" + numeroTelefono + "'" +
            ", mensaje='" + mensaje + "'" +
            ", fechaHoraRecibido='" + fechaHoraRecibido + "'" +
            '}';
    }
}
