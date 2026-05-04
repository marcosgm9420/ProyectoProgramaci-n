package basededatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import personaje.Personaje;
import personaje.Mercenario;
import personaje.Netrunner;
import personaje.Doctor;

/**
 * Clase GestorBD: gestiona todas las operaciones de acceso a la base de datos MySQL.
 * Actúa como capa de persistencia del juego RPG, permitiendo guardar, cargar,
 * actualizar y eliminar partidas y personajes.
 */
public class GestorBD {

    private static final String URL      = "jdbc:mysql://localhost:3306/rpg_game";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = "";

    /*
     * Abre y devuelve una conexión activa a la base de datos.
     * Si la conexión falla, imprime el error y devuelve null.
     * Se usa en todos los demás métodos como punto de entrada a la BD.
     */
    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos.");
            e.printStackTrace();
        }
        return conexion;
    }

    /*
     * Crea un nuevo registro en la tabla 'partidas' con el nombre del jugador
     * y el turno inicial a 1. Devuelve el ID autogenerado por la BD,
     * necesario para asociar personajes a esa partida.
     * Devuelve -1 si la inserción falla.
     */
    public static int crearNuevaPartida(String nombreJugador) {
        String sql = "INSERT INTO partidas (nombre_jugador, turno_actual) VALUES (?, 1)";
        int idPartidaGenerado = -1;

        // try-with-resources: cierra automáticamente la conexión y el PreparedStatement al terminar
        try (Connection conexion = conectar();
             // RETURN_GENERATED_KEYS indica a JDBC que queremos recuperar el ID autogenerado
             PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, nombreJugador); // Sustituye el primer '?' por el nombre del jugador
            pstmt.executeUpdate();             // Ejecuta el INSERT

            // Obtenemos el ID que MySQL asignó automáticamente a la nueva fila
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                idPartidaGenerado = rs.getInt(1); // Columna 1 del ResultSet = ID generado
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return idPartidaGenerado;
    }

    /**
     * Inserta un personaje nuevo en la tabla 'personajes_partida',
     * vinculándolo a una partida concreta mediante idPartida.
     * El booleano esAliado distingue si pertenece al equipo del jugador (true)
     * o al equipo enemigo (false).
     */
    public static void guardarPersonaje(int idPartida, String nombre, String clasePersonaje,
                                        int vidaActual, int vidaMax, int energia, boolean esAliado) {
        String sql = "INSERT INTO personajes_partida (id_partida, nombre, clase, vida_actual, vida_max, energia, es_aliado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexion = conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            // Asignamos cada '?' con el valor correspondiente, respetando el orden del SQL
            pstmt.setInt(1, idPartida);
            pstmt.setString(2, nombre);
            pstmt.setString(3, clasePersonaje);
            pstmt.setInt(4, vidaActual);
            pstmt.setInt(5, vidaMax);
            pstmt.setInt(6, energia);
            pstmt.setBoolean(7, esAliado);
            pstmt.executeUpdate(); // Ejecuta el INSERT

        } catch (SQLException e) { e.printStackTrace(); }
    }

    /**
     * Consulta y muestra por consola todas las partidas almacenadas en la BD,
     * indicando su ID, nombre del jugador y turno en el que se encuentra.
     * Si no existe ninguna, informa al usuario.
     */
    public static void listarPartidas() {
        String sql = "SELECT * FROM partidas";
        try (Connection conexion = conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) { // Ejecuta el SELECT y almacena los resultados

            System.out.println("\n=== PARTIDAS GUARDADAS ===");
            boolean hayPartidas = false;

            // Iteramos fila a fila sobre los resultados de la consulta
            while (rs.next()) {
                hayPartidas = true;
                System.out.println("ID: " + rs.getInt("id_partida")
                        + " | Jugador: " + rs.getString("nombre_jugador")
                        + " | Turno: "   + rs.getInt("turno_actual"));
            }
            if (!hayPartidas) {
                System.out.println("No hay partidas guardadas.");
            }
            System.out.println("==========================\n");

        } catch (SQLException e) { e.printStackTrace(); }
    }

    /**
     * Recupera de la BD todos los personajes de una partida filtrando por bando
     * (aliados o enemigos) y los reconstruye como objetos Java de su clase correcta.
     * Devuelve un ArrayList listo para usarse en la lógica del juego.
     */
    public static ArrayList<Personaje> cargarPersonajesPorBando(int idPartida, boolean esAliado) {
        ArrayList<Personaje> lista = new ArrayList<>();
        String sql = "SELECT nombre, clase, vida_actual, vida_max, energia "
                   + "FROM personajes_partida "
                   + "WHERE id_partida = ? AND es_aliado = ?";

        try (Connection conexion = conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, idPartida);
            pstmt.setBoolean(2, esAliado);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String nombre     = rs.getString("nombre");
                String clase      = rs.getString("clase");
                int    vidaActual = rs.getInt("vida_actual");
                int    energia    = rs.getInt("energia");

                Personaje p = null;

                // Patrón de reconstrucción de objetos 
                // según el texto guardado en la columna 'clase', instanciamos la subclase correcta
                switch (clase) {
                    case "Mercenario": p = new Mercenario(nombre); break;
                    case "Netrunner":  p = new Netrunner(nombre);  break;
                    case "Doctor":     p = new Doctor(nombre);     break;
                    default:
                        System.out.println("Clase desconocida en BD: " + clase);
                        break;
                }

                if (p != null) {
                    // Sobreescribimos los valores por defecto del constructor
                    // con los datos reales guardados en la BD
                    p.setVidaActual(vidaActual);
                    p.setEnergia(energia);
                    lista.add(p);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return lista;
    }

    /**
     * Actualiza en la BD la vida y energía de un personaje concreto dentro de una partida.
     * Se llama al final de cada turno para sincronizar el estado del juego con la persistencia.
     * La búsqueda se hace por idPartida + nombre, que actúan como clave compuesta.
     */
    public static void actualizarPersonaje(int idPartida, String nombre, int vidaActual, int energia) {
        String sql = "UPDATE personajes_partida SET vida_actual = ?, energia = ? "
                   + "WHERE id_partida = ? AND nombre = ?";
        try (Connection conexion = conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, vidaActual);
            pstmt.setInt(2, energia);
            pstmt.setInt(3, idPartida);
            pstmt.setString(4, nombre);
            pstmt.executeUpdate(); // Ejecuta el UPDATE

        } catch (SQLException e) { e.printStackTrace(); }
    }

    /**
     * Actualiza el turno actual de una partida en la BD.
     * Permite retomar la partida en el punto exacto donde se guardó.
     */
    public static void actualizarTurno(int idPartida, int turnoActual) {
        String sql = "UPDATE partidas SET turno_actual = ? WHERE id_partida = ?";
        try (Connection conexion = conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, turnoActual);
            pstmt.setInt(2, idPartida);
            pstmt.executeUpdate(); // Ejecuta el UPDATE

        } catch (SQLException e) { e.printStackTrace(); }
    }

    /**
     * Elimina una partida de la BD por su ID.
     * Gracias a la restricción ON DELETE CASCADE en la BD, al borrar la partida
     * se eliminan automáticamente también todos sus personajes asociados.
     * Informa por consola si la operación tuvo éxito o si el ID no existía.
     */
    public static void borrarPartida(int idPartida) {
        String sql = "DELETE FROM partidas WHERE id_partida = ?";
        try (Connection conexion = conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, idPartida);
            int filasBorradas = pstmt.executeUpdate(); // Devuelve el número de filas afectadas

            if (filasBorradas > 0) {
                System.out.println("Partida " + idPartida + " borrada correctamente.");
            } else {
                // Si no se borró ninguna fila, el ID no existía en la tabla
                System.out.println("No se encontró la partida con ID " + idPartida + ".");
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}