package com.proyecto_vm.service;

import com.proyecto_vm.domain.*;
import com.proyecto_vm.repository.CitaRepository;
import com.proyecto_vm.repository.FacturaRepository;
import com.proyecto_vm.repository.VentaRepository;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import com.proyecto_vm.domain.EstadoFactura; 

@Service
public class CarritoService {
    private static final String ATTRIBUTE_CARRITO = "carrito";
    
    private final DoctorService doctorService;
    private final LibroService libroService;
    private final EjercicioService ejercicioService;
    private final AlimentoService alimentoService;

    private final FacturaRepository facturaRepository;
    private final CitaRepository citaRepository;
    private final VentaRepository ventaRepository;

    public CarritoService(
            DoctorService doctorService, LibroService libroService, EjercicioService ejercicioService, AlimentoService alimentoService,
            FacturaRepository facturaRepository, CitaRepository citaRepository, VentaRepository ventaRepository) {
        this.doctorService = doctorService;
        this.libroService = libroService;
        this.ejercicioService = ejercicioService;
        this.alimentoService = alimentoService;
        this.facturaRepository = facturaRepository;
        this.citaRepository = citaRepository;
        this.ventaRepository = ventaRepository;
    } 

    public List<Item> obtenerCarrito(HttpSession session) {
        List<Item> carrito = (List<Item>) session.getAttribute(ATTRIBUTE_CARRITO);
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        return carrito;
    }
    
    public void guardarCarrito(HttpSession session, List<Item> carrito) {
        session.setAttribute(ATTRIBUTE_CARRITO, carrito);
    }
    
    public Item buscarItem(List<Item> carrito, Long idEntidad, String tipoItem) {
        if (carrito == null) {
            return null;
        }
        
        return carrito.stream()
            .filter(item -> item.getIdEntidad() != null && item.getIdEntidad().equals(idEntidad) && item.getTipoItem().equals(tipoItem))
            .findFirst()
            .orElse(null);
    }
    
    public void agregarItem(List<Item> carrito, Long idEntidad, String tipoItem, Integer cantidad, LocalDate fechaCita, LocalTime horaCita) {
        
        Item itemBD = obtenerItemDesdeBD(idEntidad, tipoItem);
        if (itemBD == null) {
             throw new RuntimeException("Ítem de tipo " + tipoItem + " con ID " + idEntidad + " no encontrado.");
        }
        
        if (tipoItem.equals("DOCTOR")) {
             if (fechaCita == null) throw new RuntimeException("Se requiere fecha para la cita.");
             
             Item citaItem = new Item();
             citaItem.setIdEntidad(idEntidad);
             citaItem.setTipoItem(tipoItem);
             citaItem.setNombre(itemBD.getNombre());
             citaItem.setPrecioUnitario(itemBD.getPrecioUnitario());
             citaItem.setCantidad(1);
             citaItem.setPrecioHistorico(itemBD.getPrecioUnitario());
             citaItem.setFechaCita(fechaCita);
             citaItem.setHoraCita(horaCita != null ? horaCita : LocalTime.of(8, 0));
             
             carrito.add(citaItem);
             return;
        }
        
        Optional<Item> itemExistente = carrito.stream()
            .filter(i -> i.getIdEntidad().equals(idEntidad) && i.getTipoItem().equals(tipoItem))
            .findFirst();

        int cantidadAAgregar = (cantidad != null && cantidad > 0) ? cantidad : 1;
        
        if (itemExistente.isPresent()) {
            Item item = itemExistente.get();
            item.setCantidad(item.getCantidad() + cantidadAAgregar);
        } else {
            Item nuevoItem = new Item();
            nuevoItem.setIdEntidad(idEntidad);
            nuevoItem.setTipoItem(tipoItem);
            nuevoItem.setNombre(itemBD.getNombre());
            nuevoItem.setPrecioUnitario(itemBD.getPrecioUnitario());
            nuevoItem.setCantidad(cantidadAAgregar);
            nuevoItem.setPrecioHistorico(itemBD.getPrecioUnitario());
            carrito.add(nuevoItem);
        }
    }
    
    private Item obtenerItemDesdeBD(Long idEntidad, String tipoItem) {
        Double precio = 0.0;
        String nombre = "";
        
        switch (tipoItem) {
            case "DOCTOR":
                Optional<Doctor> docOpt = doctorService.getDoctor(idEntidad.intValue());
                if (docOpt.isPresent()) {
                    Doctor doctor = docOpt.get();
                    precio = doctor.getTarifa();
                    nombre = "Cita con Dr(a). " + doctor.getNombre();
                }
                break;
            case "LIBRO":
                Optional<Libro> libOpt = libroService.getLibro(idEntidad.intValue());
                if (libOpt.isPresent()) {
                    Libro libro = libOpt.get();
                    precio = libro.getPrecio();
                    nombre = libro.getTitulo();
                }
                break;
            case "EJERCICIO":
                Optional<Ejercicio> ejerOpt = ejercicioService.getEjercicio(idEntidad);
                if (ejerOpt.isPresent()) {
                    Ejercicio ejercicio = ejerOpt.get();
                    precio = ejercicio.getPrecio();
                    nombre = ejercicio.getNombre();
                }
                break;
            case "ALIMENTO":
                Optional<Alimento> aliOpt = alimentoService.getAlimento(idEntidad);
                if (aliOpt.isPresent()) {
                    Alimento alimento = aliOpt.get();
                    precio = alimento.getPrecio();
                    nombre = alimento.getNombre();
                }
                break;
            default:
                return null;
        }

        if (precio == 0.0) return null;

        Item item = new Item();
        item.setPrecioUnitario(precio);
        item.setNombre(nombre);
        return item;
    }

    public void eliminarItem(List<Item> carrito, Long idEntidad, String tipoItem) {
        carrito.removeIf(item -> item.getIdEntidad() != null && item.getIdEntidad().equals(idEntidad) && item.getTipoItem().equals(tipoItem));
    }
    
    public void actualizarCantidad(List<Item> carrito, Long idEntidad, String tipoItem, int nuevaCantidad) {
        if (nuevaCantidad <= 0) {
            eliminarItem(carrito, idEntidad, tipoItem);
            return;
        }

        Optional<Item> itemExistente = carrito.stream()
            .filter(i -> i.getIdEntidad() != null && i.getIdEntidad().equals(idEntidad) && i.getTipoItem().equals(tipoItem))
            .findFirst();

        if (itemExistente.isPresent()) {
            Item item = itemExistente.get();
            if (item.getTipoItem().equals("DOCTOR")) {
                throw new RuntimeException("No se puede modificar la cantidad de una cita.");
            }
            item.setCantidad(nuevaCantidad);
        }
    }

    public int contarUnidades(List<Item> carrito) {
        if (carrito == null || carrito.isEmpty()) {
            return 0;
        }
        return carrito.stream().mapToInt(Item::getCantidad).sum();
    }
    
    public Double calcularTotal(List<Item> carrito) {
        return carrito.stream()
            .mapToDouble(Item::getSubTotal)
            .sum();
    }
    
    public void limpiarCarrito(HttpSession session) {
        List<Item> carrito = obtenerCarrito(session);
        if (carrito != null) {
            carrito.clear();
        }
        guardarCarrito(session, carrito);
    }

    @Transactional
    public Factura procesarCompra(List<Item> carrito, Usuario usuario) {
        if (carrito == null || carrito.isEmpty()) {
            throw new RuntimeException("El carrito está vacío para procesar la compra.");
        }
        
        Factura facturaProductos = null;
        LocalDateTime ahora = LocalDateTime.now();
        Double totalProductos = carrito.stream()
            .filter(i -> !i.getTipoItem().equals("DOCTOR"))
            .mapToDouble(Item::getSubTotal).sum();
        
        if (totalProductos > 0.0) {
            facturaProductos = new Factura();
            facturaProductos.setUsuario(usuario);
            facturaProductos.setFecha(ahora);
            facturaProductos.setTotal(totalProductos);
            facturaProductos.setEstado(EstadoFactura.PAGADA);
            facturaProductos.setFechaCreacion(ahora);
            facturaProductos.setFechaModificacion(ahora);
            facturaProductos = facturaRepository.save(facturaProductos);
        }
        
        for (Item item : carrito) {
            if (item.getTipoItem().equals("DOCTOR")) {
                Cita cita = new Cita();
                Doctor doctor = doctorService.getDoctor(item.getIdEntidad().intValue()).orElseThrow(() -> new RuntimeException("Doctor no encontrado."));
                cita.setDoctor(doctor);
                cita.setUsuario(usuario);
                cita.setFechaCita(item.getFechaCita());
                cita.setHoraCita(item.getHoraCita());
                cita.setTarifaAcordada(item.getPrecioHistorico());
                cita.setEstadoCita("PENDIENTE"); 
                citaRepository.save(cita);
                
            } else if (facturaProductos != null) {
                Venta venta = new Venta();
                venta.setFactura(facturaProductos);
                venta.setIdEntidad(item.getIdEntidad());
                venta.setTipoEntidad(item.getTipoItem());
                venta.setPrecioHistorico(item.getPrecioHistorico());
                venta.setCantidad(item.getCantidad());
                venta.setFechaCreacion(ahora);
                venta.setFechaModificacion(ahora);
                ventaRepository.save(venta);
            }
        }
        
        if (facturaProductos != null) {
            return facturaProductos;
        } else if (!carrito.isEmpty()) {
            return new Factura(); 
        } else {
            return null;
        }
    }
}