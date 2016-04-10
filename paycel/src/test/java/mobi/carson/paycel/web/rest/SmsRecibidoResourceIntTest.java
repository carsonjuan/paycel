package mobi.carson.paycel.web.rest;

import mobi.carson.paycel.PaycelApp;
import mobi.carson.paycel.domain.SmsRecibido;
import mobi.carson.paycel.repository.SmsRecibidoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SmsRecibidoResource REST controller.
 *
 * @see SmsRecibidoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PaycelApp.class)
@WebAppConfiguration
@IntegrationTest
public class SmsRecibidoResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NUMERO_TELEFONO = "AAAAA";
    private static final String UPDATED_NUMERO_TELEFONO = "BBBBB";
    private static final String DEFAULT_MENSAJE = "AAAAA";
    private static final String UPDATED_MENSAJE = "BBBBB";

    private static final ZonedDateTime DEFAULT_FECHA_HORA_RECIBIDO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_FECHA_HORA_RECIBIDO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_FECHA_HORA_RECIBIDO_STR = dateTimeFormatter.format(DEFAULT_FECHA_HORA_RECIBIDO);

    @Inject
    private SmsRecibidoRepository smsRecibidoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSmsRecibidoMockMvc;

    private SmsRecibido smsRecibido;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SmsRecibidoResource smsRecibidoResource = new SmsRecibidoResource();
        ReflectionTestUtils.setField(smsRecibidoResource, "smsRecibidoRepository", smsRecibidoRepository);
        this.restSmsRecibidoMockMvc = MockMvcBuilders.standaloneSetup(smsRecibidoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        smsRecibido = new SmsRecibido();
        smsRecibido.setNumeroTelefono(DEFAULT_NUMERO_TELEFONO);
        smsRecibido.setMensaje(DEFAULT_MENSAJE);
        smsRecibido.setFechaHoraRecibido(DEFAULT_FECHA_HORA_RECIBIDO);
    }

    @Test
    @Transactional
    public void createSmsRecibido() throws Exception {
        int databaseSizeBeforeCreate = smsRecibidoRepository.findAll().size();

        // Create the SmsRecibido

        restSmsRecibidoMockMvc.perform(post("/api/sms-recibidos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsRecibido)))
                .andExpect(status().isCreated());

        // Validate the SmsRecibido in the database
        List<SmsRecibido> smsRecibidos = smsRecibidoRepository.findAll();
        assertThat(smsRecibidos).hasSize(databaseSizeBeforeCreate + 1);
        SmsRecibido testSmsRecibido = smsRecibidos.get(smsRecibidos.size() - 1);
        assertThat(testSmsRecibido.getNumeroTelefono()).isEqualTo(DEFAULT_NUMERO_TELEFONO);
        assertThat(testSmsRecibido.getMensaje()).isEqualTo(DEFAULT_MENSAJE);
        assertThat(testSmsRecibido.getFechaHoraRecibido()).isEqualTo(DEFAULT_FECHA_HORA_RECIBIDO);
    }

    @Test
    @Transactional
    public void getAllSmsRecibidos() throws Exception {
        // Initialize the database
        smsRecibidoRepository.saveAndFlush(smsRecibido);

        // Get all the smsRecibidos
        restSmsRecibidoMockMvc.perform(get("/api/sms-recibidos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(smsRecibido.getId().intValue())))
                .andExpect(jsonPath("$.[*].numeroTelefono").value(hasItem(DEFAULT_NUMERO_TELEFONO.toString())))
                .andExpect(jsonPath("$.[*].mensaje").value(hasItem(DEFAULT_MENSAJE.toString())))
                .andExpect(jsonPath("$.[*].fechaHoraRecibido").value(hasItem(DEFAULT_FECHA_HORA_RECIBIDO_STR)));
    }

    @Test
    @Transactional
    public void getSmsRecibido() throws Exception {
        // Initialize the database
        smsRecibidoRepository.saveAndFlush(smsRecibido);

        // Get the smsRecibido
        restSmsRecibidoMockMvc.perform(get("/api/sms-recibidos/{id}", smsRecibido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(smsRecibido.getId().intValue()))
            .andExpect(jsonPath("$.numeroTelefono").value(DEFAULT_NUMERO_TELEFONO.toString()))
            .andExpect(jsonPath("$.mensaje").value(DEFAULT_MENSAJE.toString()))
            .andExpect(jsonPath("$.fechaHoraRecibido").value(DEFAULT_FECHA_HORA_RECIBIDO_STR));
    }

    @Test
    @Transactional
    public void getNonExistingSmsRecibido() throws Exception {
        // Get the smsRecibido
        restSmsRecibidoMockMvc.perform(get("/api/sms-recibidos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmsRecibido() throws Exception {
        // Initialize the database
        smsRecibidoRepository.saveAndFlush(smsRecibido);
        int databaseSizeBeforeUpdate = smsRecibidoRepository.findAll().size();

        // Update the smsRecibido
        SmsRecibido updatedSmsRecibido = new SmsRecibido();
        updatedSmsRecibido.setId(smsRecibido.getId());
        updatedSmsRecibido.setNumeroTelefono(UPDATED_NUMERO_TELEFONO);
        updatedSmsRecibido.setMensaje(UPDATED_MENSAJE);
        updatedSmsRecibido.setFechaHoraRecibido(UPDATED_FECHA_HORA_RECIBIDO);

        restSmsRecibidoMockMvc.perform(put("/api/sms-recibidos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSmsRecibido)))
                .andExpect(status().isOk());

        // Validate the SmsRecibido in the database
        List<SmsRecibido> smsRecibidos = smsRecibidoRepository.findAll();
        assertThat(smsRecibidos).hasSize(databaseSizeBeforeUpdate);
        SmsRecibido testSmsRecibido = smsRecibidos.get(smsRecibidos.size() - 1);
        assertThat(testSmsRecibido.getNumeroTelefono()).isEqualTo(UPDATED_NUMERO_TELEFONO);
        assertThat(testSmsRecibido.getMensaje()).isEqualTo(UPDATED_MENSAJE);
        assertThat(testSmsRecibido.getFechaHoraRecibido()).isEqualTo(UPDATED_FECHA_HORA_RECIBIDO);
    }

    @Test
    @Transactional
    public void deleteSmsRecibido() throws Exception {
        // Initialize the database
        smsRecibidoRepository.saveAndFlush(smsRecibido);
        int databaseSizeBeforeDelete = smsRecibidoRepository.findAll().size();

        // Get the smsRecibido
        restSmsRecibidoMockMvc.perform(delete("/api/sms-recibidos/{id}", smsRecibido.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SmsRecibido> smsRecibidos = smsRecibidoRepository.findAll();
        assertThat(smsRecibidos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
