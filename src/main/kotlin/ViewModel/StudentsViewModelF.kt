package ViewModel

import Files.IGestorFichero
import ViewModel.IStudentsVM.Companion.MAXCARACTERES
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class  StudentsViewModelIF(
    private val gestorFichero : IGestorFichero,
    private val archivoEstudiantes : File
): IStudentsVM {



    private val _newStudent = mutableStateOf("")
    override val newStudent: State<String> = _newStudent

    private val _students = mutableStateListOf<String>()
    override var students = _students

    private val _showInfoMessage = mutableStateOf(false)
    override val showInfoMessage: State<Boolean> = _showInfoMessage

    private val _infoMessage = mutableStateOf("")
    override val infoMessage: State<String> = _infoMessage

    private val _selectedIndex = mutableStateOf(-1) // -1 significa que no hay selección
    override val indiceSeleccionado: State<Int> = _selectedIndex

    override fun addEstudiante() {
        if (_newStudent.value.isNotBlank()) {
            _students.add(_newStudent.value.trim())
            _newStudent.value = ""
        }
    }

    override fun newStudentChange(name:String) {
        if (name.length <= MAXCARACTERES) {
            _newStudent.value = name
        }
    }

    override fun borrarEstudiante(indice: Int) {
        if (indice in _students.indices){
            _students.removeAt(indice)
        }
    }



    override fun cargarEstudiantes() {
        val contenidoFichero = gestorFichero.leer(archivoEstudiantes)
        if (contenidoFichero != null){
            _students.addAll(contenidoFichero)
        }
        else{
            updateInfoMessage("No hay datos de los estudiantes")
        }
    }

    override fun guardarEstudiante() {
        var error = ""
        val newStudentsFile = gestorFichero.crearFichero(archivoEstudiantes.absolutePath)
        if (newStudentsFile != null) {
            for (student in students) {
                error = gestorFichero.escribir(archivoEstudiantes, "$student\n")
                if (error.isNotEmpty()) {
                    break
                }
            }
            if (error.isNotEmpty()) {
                updateInfoMessage(error)
            } else {
                updateInfoMessage("Fichero guardado correctamente")
            }
        } else {
            updateInfoMessage("No se pudo generar el fichero studentList.txt")
        }
    }


    override fun showInfoMesssage(show: Boolean) {
        _showInfoMessage.value = show
    }

    private fun updateInfoMessage(message: String){
        _infoMessage.value = message
        _showInfoMessage.value = true
        CoroutineScope(Dispatchers.Default).launch {
            delay(2000)
            _showInfoMessage.value = false
            _infoMessage.value = ""
        }
    }

    override fun vaciarEstudiantes() {
        _students.clear()
    }

    override fun estudianteSeleccionado(indice: Int) {
        _selectedIndex.value = indice
    }


}