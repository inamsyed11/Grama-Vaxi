package com.example.gramavaxi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*

// ─────────────────────────────────────────────────────────────────────────────
//  DATA MODELS
// ─────────────────────────────────────────────────────────────────────────────

enum class Species(val emoji: String, val label: String, val kannadaName: String) {
    COW("🐄", "Cow", "ಹಸು"),
    BUFFALO("🐃", "Buffalo", "ಎಮ್ಮೆ"),
    GOAT("🐐", "Goat", "ಮೇಕೆ"),
    SHEEP("🐑", "Sheep", "ಕುರಿ"),
    DUCK("🦆", "Duck", "ಬಾತುಕೋಳಿ"),
    HEN("🐔", "Hen", "ಕೋಳಿ"),
    PIG("🐷", "Pig", "ಹಂದಿ"),
    RABBIT("🐇", "Rabbit", "ಮೊಲ")
}

enum class VaccineStatus(val label: String, val color: Color) {
    DONE("Vaccinated", Color(0xFF43A047)),
    DUE_SOON("Due Soon", Color(0xFFF57F17)),
    OVERDUE("Overdue", Color(0xFFC62828)),
    SCHEDULED("Scheduled", Color(0xFF1565C0))
}

data class VaccineRecord(
    val name: String,
    val date: String,
    val status: VaccineStatus
)

data class Camp(
    val id: Int,
    val name: String,
    val location: String,
    val date: String,
    val time: String,
    val district: String,
    val doctorId: Int,
    val address: String,
    val landmark: String
)

data class Doctor(
    val id: Int,
    val name: String,
    val qualification: String,
    val specialisation: String,
    val phone: String,
    val altPhone: String,
    val email: String,
    val clinicName: String,
    val clinicAddress: String,
    val district: String,
    val pincode: String,
    val timings: String,
    val experience: Int,
    val registrationNo: String
)



// ─────────────────────────────────────────────────────────────────────────────
//  COLOUR PALETTE  (dark-green rural theme, WCAG-safe on dark bg)
// ─────────────────────────────────────────────────────────────────────────────

object GV {
    val BgPrimary   = Color(0xFF0D1B0F)
    val BgSurface   = Color(0xFF132416)
    val BgCard      = Color(0xFF1A2E1D)
    val BgCardAlt   = Color(0xFF1E3521)
    val Green900    = Color(0xFF1B5E20)
    val Green700    = Color(0xFF2E7D32)
    val Green500    = Color(0xFF43A047)
    val Green300    = Color(0xFF81C784)
    val Green100    = Color(0xFFC8E6C9)
    val Amber       = Color(0xFFF57F17)
    val AmberLight  = Color(0xFFFFD54F)
    val Red         = Color(0xFFC62828)
    val RedLight    = Color(0xFFEF9A9A)
    val Blue        = Color(0xFF1565C0)
    val BlueLight   = Color(0xFF90CAF9)
    val TextPrim    = Color(0xFFF1F8E9)
    val TextSec     = Color(0xFFA5D6A7)
    val TextMuted   = Color(0xFF558B5B)
    val Divider     = Color(0xFF2E4A30)
    val White       = Color.White
}

// ─────────────────────────────────────────────────────────────────────────────
//  STATIC DATA — 3 Doctors
// ─────────────────────────────────────────────────────────────────────────────

fun getDoctors(): List<Doctor> = listOf(
    Doctor(
        id = 1,
        name = "Dr. Suresh Kumar R.",
        qualification = "B.V.Sc & AH, M.V.Sc (Veterinary Medicine)",
        specialisation = "Bovine & Small Ruminant Health",
        phone = "+91 98450 11223",
        altPhone = "+91 80-2345 6789",
        email = "suresh.kumar@kvhc.karnataka.gov.in",
        clinicName = "Govt. Veterinary Hospital, Tumkur",
        clinicAddress = "Near Bus Stand, B.H. Road, Tumkur",
        district = "Tumkur",
        pincode = "572101",
        timings = "Mon–Sat: 9:00 AM – 5:00 PM",
        experience = 18,
        registrationNo = "KVS-2006-T-447"
    ),
    Doctor(
        id = 2,
        name = "Dr. Kavitha Nagaraj",
        qualification = "B.V.Sc & AH, Ph.D (Poultry Science)",
        specialisation = "Poultry, Swine & Small Animal Care",
        phone = "+91 97420 55678",
        altPhone = "+91 80-4567 8901",
        email = "kavitha.nagaraj@kvhc.karnataka.gov.in",
        clinicName = "Govt. Veterinary Dispensary, Kolar",
        clinicAddress = "K.G.F. Road, Beside Civil Hospital, Kolar",
        district = "Kolar",
        pincode = "563101",
        timings = "Mon–Fri: 8:30 AM – 4:30 PM",
        experience = 12,
        registrationNo = "KVS-2012-K-891"
    ),
    Doctor(
        id = 3,
        name = "Dr. Ravi Shankar M.",
        qualification = "B.V.Sc & AH, M.V.Sc (Animal Reproduction)",
        specialisation = "Dairy Cattle & Buffalo Reproduction",
        phone = "+91 99800 33445",
        altPhone = "+91 80-6789 0123",
        email = "ravi.shankar@kvhc.karnataka.gov.in",
        clinicName = "BBMP Veterinary Centre, Bangalore",
        clinicAddress = "No. 14, Veterinary Lane, Jayanagar 4th Block, Bangalore",
        district = "Bangalore Urban",
        pincode = "560011",
        timings = "Mon–Sat: 8:00 AM – 6:00 PM",
        experience = 22,
        registrationNo = "KVS-2002-B-112"
    )
)

// ─────────────────────────────────────────────────────────────────────────────
//  STATIC DATA — 3 Camps
// ─────────────────────────────────────────────────────────────────────────────

fun getCamps(): List<Camp> = listOf(
    Camp(
        id = 1,
        name = "Tumkur Camp",
        location = "Vasantha Mahal Grounds",
        date = "January 8, 2025",
        time = "9:00 AM – 3:00 PM",
        district = "Tumkur",
        doctorId = 1,
        address = "Vasantha Mahal Grounds, S.S. Puram, Tumkur – 572101",
        landmark = "Near Siddaganga Math"
    ),
    Camp(
        id = 2,
        name = "Kolar Camp",
        location = "Gram Panchayat Grounds",
        date = "January 12, 2025",
        time = "10:00 AM – 4:00 PM",
        district = "Kolar",
        doctorId = 2,
        address = "Gram Panchayat Open Grounds, Bangarpet Road, Kolar – 563101",
        landmark = "Opposite Old Bus Stand"
    ),
    Camp(
        id = 3,
        name = "Bangalore Camp",
        location = "BBMP Veterinary Centre",
        date = "January 15, 2025",
        time = "8:00 AM – 2:00 PM",
        district = "Bangalore Urban",
        doctorId = 3,
        address = "No. 14, Veterinary Lane, Jayanagar 4th Block, Bangalore – 560011",
        landmark = "Behind Jayanagar Shopping Complex"
    )
)

// ─────────────────────────────────────────────────────────────────────────────
//  STATIC DATA — 75 Animals (25 per camp)
// ─────────────────────────────────────────────────────────────────────────────



// ─────────────────────────────────────────────────────────────────────────────
//  NAV DESTINATIONS
// ─────────────────────────────────────────────────────────────────────────────

sealed class Screen {
    object Home : Screen()
    object CampList : Screen()
    object DoctorList : Screen()
    data class AnimalDetail(val animal: Animal) : Screen()
    data class CampDetail(val camp: Camp) : Screen()
    data class DoctorDetail(val doctor: Doctor) : Screen()
}

// ─────────────────────────────────────────────────────────────────────────────
//  MAIN ACTIVITY
// ─────────────────────────────────────────────────────────────────────────────

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                GramaVaxiApp()
            }
        }
    }
}

@Composable
fun GramaVaxiApp() {
    val allAnimals = remember { getAnimals() }
    val allCamps   = remember { getCamps() }
    val allDoctors = remember { getDoctors() }

    var screen: Screen by remember { mutableStateOf(Screen.Home) }

    AnimatedContent(
        targetState = screen,
        transitionSpec = {
            (slideInHorizontally { it } + fadeIn()) togetherWith
                    (slideOutHorizontally { -it } + fadeOut())
        },
        label = "screen"
    ) { current ->
        when (current) {
            is Screen.Home         -> HomeScreen(allAnimals, allCamps) { screen = it }
            is Screen.CampList     -> CampListScreen(allCamps, allAnimals, allDoctors) { screen = it }
            is Screen.DoctorList   -> DoctorListScreen(allDoctors) { screen = it }
            is Screen.AnimalDetail -> AnimalDetailScreen(current.animal, allCamps, allDoctors) { screen = Screen.Home }
            is Screen.CampDetail   -> CampDetailScreen(current.camp, allAnimals, allDoctors) { screen = Screen.CampList }
            is Screen.DoctorDetail -> DoctorDetailScreen(current.doctor, allCamps) { screen = Screen.DoctorList }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  HOME SCREEN
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun HomeScreen(animals: List<Animal>, camps: List<Camp>, onNav: (Screen) -> Unit) {
    val overdue  = animals.count { it.vaccineStatus == VaccineStatus.OVERDUE }
    val dueSoon  = animals.count { it.vaccineStatus == VaccineStatus.DUE_SOON }
    val done     = animals.count { it.vaccineStatus == VaccineStatus.DONE }

    var searchQuery by remember { mutableStateOf("") }
    var filterSpecies by remember { mutableStateOf<Species?>(null) }

    val filtered = remember(searchQuery, filterSpecies) {
        animals.filter { a ->
            (searchQuery.isBlank() || a.name.contains(searchQuery, true) ||
                    a.species.label.contains(searchQuery, true) ||
                    a.ownerName.contains(searchQuery, true)) &&
                    (filterSpecies == null || a.species == filterSpecies)
        }
    }

    Column(Modifier.fillMaxSize().background(GV.BgPrimary)) {
        // Top bar
        Column(
            Modifier.fillMaxWidth().background(GV.Green900).padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🌾", fontSize = 26.sp)
                Spacer(Modifier.width(8.dp))
                Column {
                    Text("Grama-Vaxi", color = GV.TextPrim, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("ಜಾನುವಾರು ಆರೋಗ್ಯ ಅಲರ್ಟ್", color = GV.Green300, fontSize = 11.sp)
                }
            }

            Spacer(Modifier.height(12.dp))

            // Camp alert banner
            val nextCamp = camps.minByOrNull { it.date }!!
            Row(
                Modifier.fillMaxWidth()
                    .background(GV.Amber.copy(alpha = 0.15f), RoundedCornerShape(10.dp))
                    .border(1.dp, GV.Amber.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🔔", fontSize = 20.sp)
                Spacer(Modifier.width(8.dp))
                Column {
                    Text("Camp Alert — 3 days away!", color = GV.AmberLight, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("${nextCamp.name} · ${nextCamp.location} · ${nextCamp.date}", color = GV.Amber, fontSize = 11.sp)
                }
            }
        }

        // Bottom nav menu
        Row(
            Modifier.fillMaxWidth().background(GV.BgSurface)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NavChip("🐄 Animals", true) {}
            NavChip("🏕 Camps", false) { onNav(Screen.CampList) }
            NavChip("👨‍⚕️ Doctors", false) { onNav(Screen.DoctorList) }
        }

        // Stats row
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(Modifier.weight(1f), "${animals.size}", "Total", GV.Green500)
            StatCard(Modifier.weight(1f), "$done", "Vaccinated", GV.Green500)
            StatCard(Modifier.weight(1f), "$dueSoon", "Due Soon", GV.Amber)
            StatCard(Modifier.weight(1f), "$overdue", "Overdue", GV.Red)
        }

        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search animals, owners…", color = GV.TextMuted, fontSize = 13.sp) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GV.Green500,
                unfocusedBorderColor = GV.Divider,
                focusedTextColor = GV.TextPrim,
                unfocusedTextColor = GV.TextPrim,
                cursorColor = GV.Green500
            ),
            singleLine = true,
            leadingIcon = { Text("🔍", fontSize = 16.sp) }
        )

        // Species filter
        val speciesList = Species.values().toList()
        LazyRow(
            Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                FilterChip(
                    selected = filterSpecies == null,
                    onClick = { filterSpecies = null },
                    label = { Text("All", fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = GV.Green700,
                        selectedLabelColor = GV.White
                    )
                )
            }
            items(speciesList) { sp ->
                FilterChip(
                    selected = filterSpecies == sp,
                    onClick = { filterSpecies = if (filterSpecies == sp) null else sp },
                    label = { Text("${sp.emoji} ${sp.label}", fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = GV.Green700,
                        selectedLabelColor = GV.White
                    )
                )
            }
        }

        Text(
            "${filtered.size} animals",
            color = GV.TextMuted, fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 2.dp)
        )

        // Animal list
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filtered, key = { it.id }) { animal ->
                AnimalCard(animal) { onNav(Screen.AnimalDetail(animal)) }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  ANIMAL CARD
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun AnimalCard(animal: Animal, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GV.BgCard),
        border = BorderStroke(0.5.dp, GV.Divider)
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            // Species icon circle
            Box(
                Modifier.size(48.dp)
                    .background(GV.Green900, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(animal.species.emoji, fontSize = 22.sp)
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(animal.name, color = GV.TextPrim, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Text(
                    "${animal.species.label} · ${animal.breed} · ${animal.age}",
                    color = GV.TextSec, fontSize = 12.sp
                )
                Text(
                    "👤 ${animal.ownerName}  •  📍 ${animal.village}",
                    color = GV.TextMuted, fontSize = 11.sp, modifier = Modifier.padding(top = 2.dp)
                )
            }

            Spacer(Modifier.width(8.dp))

            // Status badge
            StatusBadge(animal.vaccineStatus)
        }
    }
}

@Composable
fun StatusBadge(status: VaccineStatus) {
    val bg = status.color.copy(alpha = 0.15f)
    Box(
        Modifier.background(bg, RoundedCornerShape(8.dp))
            .border(1.dp, status.color.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(status.label, color = status.color, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  ANIMAL DETAIL SCREEN
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun AnimalDetailScreen(animal: Animal, camps: List<Camp>, doctors: List<Doctor>, onBack: () -> Unit) {
    val camp   = camps.firstOrNull { it.id == animal.campId }
    val doctor = camp?.let { c -> doctors.firstOrNull { it.id == c.doctorId } }

    Column(Modifier.fillMaxSize().background(GV.BgPrimary)) {
        // Header
        Column(Modifier.fillMaxWidth().background(GV.Green900).padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, null, tint = GV.White)
                }
                Spacer(Modifier.width(8.dp))
                Text("Health Card", color = GV.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                // Identity card
                Card(
                    Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = GV.BgCard),
                    border = BorderStroke(1.dp, GV.Green700)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(64.dp).background(GV.Green900, CircleShape), Alignment.Center) {
                                Text(animal.species.emoji, fontSize = 32.sp)
                            }
                            Spacer(Modifier.width(14.dp))
                            Column {
                                Text(animal.name, color = GV.TextPrim, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                                Text("${animal.species.label} (${animal.species.kannadaName})", color = GV.Green300, fontSize = 13.sp)
                                Spacer(Modifier.height(4.dp))
                                StatusBadge(animal.vaccineStatus)
                            }
                        }
                        Spacer(Modifier.height(14.dp))
                        Divider(color = GV.Divider)
                        Spacer(Modifier.height(12.dp))
                        Row(Modifier.fillMaxWidth()) {
                            DetailField(Modifier.weight(1f), "Tag ID", animal.tagId)
                            DetailField(Modifier.weight(1f), "Breed", animal.breed)
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(Modifier.fillMaxWidth()) {
                            DetailField(Modifier.weight(1f), "Age", animal.age)
                            DetailField(Modifier.weight(1f), "Gender", animal.gender)
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(Modifier.fillMaxWidth()) {
                            DetailField(Modifier.weight(1f), "Weight", animal.weight)
                            DetailField(Modifier.weight(1f), "Colour", animal.colour)
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(Modifier.fillMaxWidth()) {
                            DetailField(Modifier.weight(1f), "Owner", animal.ownerName)
                            DetailField(Modifier.weight(1f), "Village", animal.village)
                        }
                    }
                }
            }

            // Vaccine timeline
            item {
                SectionTitle("💉 Vaccine Timeline")
            }
            items(animal.vaccines) { record ->
                VaccineTimelineItem(record)
            }

            // Camp info
            if (camp != null) {
                item {
                    SectionTitle("🏕 Assigned Camp")
                    Card(
                        Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = GV.BgCard),
                        border = BorderStroke(0.5.dp, GV.Divider)
                    ) {
                        Column(Modifier.padding(14.dp)) {
                            Text(camp.name, color = GV.TextPrim, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                            Spacer(Modifier.height(4.dp))
                            InfoRow("📍", camp.address)
                            InfoRow("⚑", camp.landmark)
                            InfoRow("📅", "${camp.date}  ·  ${camp.time}")
                        }
                    }
                }
            }

            // Doctor info
            if (doctor != null) {
                item {
                    SectionTitle("👨‍⚕️ Assigned Doctor")
                    DoctorMiniCard(doctor)
                }
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun VaccineTimelineItem(record: VaccineRecord) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.size(12.dp).background(record.status.color, CircleShape))
            Box(Modifier.width(2.dp).height(36.dp).background(GV.Divider))
        }
        Spacer(Modifier.width(12.dp))
        Card(
            Modifier.fillMaxWidth().padding(bottom = 4.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = GV.BgCard),
            border = BorderStroke(0.5.dp, record.status.color.copy(alpha = 0.25f))
        ) {
            Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(record.name, color = GV.TextPrim, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    Text(record.date, color = GV.TextMuted, fontSize = 11.sp)
                }
                StatusBadge(record.status)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  CAMP LIST SCREEN
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun CampListScreen(camps: List<Camp>, animals: List<Animal>, doctors: List<Doctor>, onNav: (Screen) -> Unit) {
    Column(Modifier.fillMaxSize().background(GV.BgPrimary)) {
        ScreenHeader("🏕 Vaccination Camps") { onNav(Screen.Home) }

        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(camps) { camp ->
                val campAnimals = animals.filter { it.campId == camp.id }
                val doctor = doctors.firstOrNull { it.id == camp.doctorId }
                CampCard(camp, campAnimals, doctor) { onNav(Screen.CampDetail(camp)) }
            }
        }
    }
}

@Composable
fun CampCard(camp: Camp, animals: List<Animal>, doctor: Doctor?, onClick: () -> Unit) {
    Card(
        Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = GV.BgCard),
        border = BorderStroke(0.5.dp, GV.Green700)
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(44.dp).background(GV.Green900, RoundedCornerShape(10.dp)), Alignment.Center) {
                    Text("🏕", fontSize = 22.sp)
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(camp.name, color = GV.TextPrim, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(camp.district, color = GV.Green300, fontSize = 12.sp)
                }
                Box(
                    Modifier.background(GV.Amber.copy(0.15f), RoundedCornerShape(8.dp))
                        .border(1.dp, GV.Amber.copy(0.4f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("Upcoming", color = GV.Amber, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(10.dp))
            Divider(color = GV.Divider)
            Spacer(Modifier.height(10.dp))

            InfoRow("📍", camp.location)
            InfoRow("📅", "${camp.date}  ·  ${camp.time}")
            InfoRow("⚑ ", camp.landmark)
            if (doctor != null) InfoRow("👨‍⚕️", doctor.name)

            Spacer(Modifier.height(8.dp))

            // Animal count chips
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MiniChip("${animals.size} Animals", GV.Green500)
                MiniChip("${animals.count { it.vaccineStatus == VaccineStatus.OVERDUE }} Overdue", GV.Red)
                MiniChip("${animals.count { it.vaccineStatus == VaccineStatus.DUE_SOON }} Due", GV.Amber)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  CAMP DETAIL SCREEN
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun CampDetailScreen(camp: Camp, animals: List<Animal>, doctors: List<Doctor>, onBack: () -> Unit) {
    val campAnimals = animals.filter { it.campId == camp.id }
    val doctor = doctors.firstOrNull { it.id == camp.doctorId }

    Column(Modifier.fillMaxSize().background(GV.BgPrimary)) {
        ScreenHeader(camp.name) { onBack() }

        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Card(
                    Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = GV.BgCard),
                    border = BorderStroke(1.dp, GV.Green700)
                ) {
                    Column(Modifier.padding(14.dp)) {
                        Text("Camp Details", color = GV.Green300, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        InfoRow("📍", camp.address)
                        InfoRow("⚑", camp.landmark)
                        InfoRow("📅", camp.date)
                        InfoRow("⏰", camp.time)
                    }
                }
            }

            if (doctor != null) {
                item {
                    SectionTitle("👨‍⚕️ Doctor")
                    DoctorMiniCard(doctor)
                }
            }

            item { SectionTitle("🐄 Animals at this Camp (${campAnimals.size})") }

            items(campAnimals, key = { it.id }) { animal ->
                AnimalCard(animal) {}
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  DOCTOR LIST SCREEN
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun DoctorListScreen(doctors: List<Doctor>, onNav: (Screen) -> Unit) {
    Column(Modifier.fillMaxSize().background(GV.BgPrimary)) {
        ScreenHeader("👨‍⚕️ Veterinary Doctors") { onNav(Screen.Home) }

        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(doctors) { doctor ->
                DoctorCard(doctor) { onNav(Screen.DoctorDetail(doctor)) }
            }
        }
    }
}

@Composable
fun DoctorCard(doctor: Doctor, onClick: () -> Unit) {
    Card(
        Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = GV.BgCard),
        border = BorderStroke(0.5.dp, GV.Divider)
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(52.dp).background(GV.Green900, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        doctor.name.split(" ").filter { it.isNotBlank() }.take(2)
                            .joinToString("") { it.first().uppercase() },
                        color = GV.Green300, fontSize = 16.sp, fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(doctor.name, color = GV.TextPrim, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    Text(doctor.qualification, color = GV.Green300, fontSize = 11.sp)
                    Text(doctor.specialisation, color = GV.TextMuted, fontSize = 11.sp)
                }
            }
            Spacer(Modifier.height(10.dp))
            Divider(color = GV.Divider)
            Spacer(Modifier.height(10.dp))
            InfoRow("🏥", doctor.clinicName)
            InfoRow("📍", "${doctor.district} – ${doctor.pincode}")
            InfoRow("📞", doctor.phone)
            InfoRow("⏰", doctor.timings)
            Spacer(Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MiniChip("${doctor.experience} yrs exp", GV.Green500)
                MiniChip("Reg: ${doctor.registrationNo}", GV.Blue)
            }
        }
    }
}

@Composable
fun DoctorMiniCard(doctor: Doctor) {
    Card(
        Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GV.BgCard),
        border = BorderStroke(0.5.dp, GV.Divider)
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(44.dp).background(GV.Green900, CircleShape), Alignment.Center) {
                Text(
                    doctor.name.split(" ").filter { it.isNotBlank() }.take(2)
                        .joinToString("") { it.first().uppercase() },
                    color = GV.Green300, fontSize = 14.sp, fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(doctor.name, color = GV.TextPrim, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Text(doctor.clinicName, color = GV.TextSec, fontSize = 11.sp)
                Text(doctor.phone, color = GV.Green300, fontSize = 11.sp)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  DOCTOR DETAIL SCREEN
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun DoctorDetailScreen(doctor: Doctor, camps: List<Camp>, onBack: () -> Unit) {
    val assignedCamps = camps.filter { it.doctorId == doctor.id }

    Column(Modifier.fillMaxSize().background(GV.BgPrimary)) {
        ScreenHeader("Doctor Profile") { onBack() }

        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                // Profile card
                Card(
                    Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = GV.BgCard),
                    border = BorderStroke(1.dp, GV.Green700)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(64.dp).background(GV.Green900, CircleShape), Alignment.Center) {
                                Text(
                                    doctor.name.split(" ").filter { it.isNotBlank() }.take(2)
                                        .joinToString("") { it.first().uppercase() },
                                    color = GV.Green300, fontSize = 22.sp, fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(Modifier.width(14.dp))
                            Column {
                                Text(doctor.name, color = GV.TextPrim, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text(doctor.qualification, color = GV.Green300, fontSize = 12.sp)
                                Text(doctor.specialisation, color = GV.TextSec, fontSize = 12.sp)
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            MiniChip("${doctor.experience} yrs", GV.Green500)
                            MiniChip(doctor.registrationNo, GV.Blue)
                        }
                    }
                }
            }

            item {
                SectionTitle("🏥 Clinic Details")
                Card(
                    Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = GV.BgCard),
                    border = BorderStroke(0.5.dp, GV.Divider)
                ) {
                    Column(Modifier.padding(14.dp)) {
                        Text(doctor.clinicName, color = GV.TextPrim, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))
                        InfoRow("📍", doctor.clinicAddress)
                        InfoRow("🏙", "${doctor.district} – ${doctor.pincode}")
                        InfoRow("📞", doctor.phone)
                        InfoRow("📞", "Alt: ${doctor.altPhone}")
                        InfoRow("✉", doctor.email)
                        InfoRow("⏰", doctor.timings)
                    }
                }
            }

            item {
                SectionTitle("🏕 Assigned Camps")
            }
            items(assignedCamps) { camp ->
                Card(
                    Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = GV.BgCard),
                    border = BorderStroke(0.5.dp, GV.Divider)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(camp.name, color = GV.TextPrim, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        InfoRow("📍", camp.location)
                        InfoRow("📅", camp.date)
                        InfoRow("⏰", camp.time)
                    }
                }
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  REUSABLE COMPOSABLES
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun ScreenHeader(title: String, onBack: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().background(GV.Green900).padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, null, tint = GV.White)
        }
        Text(title, color = GV.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun NavChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        Modifier.clickable(onClick = onClick)
            .background(
                if (selected) GV.Green700 else GV.BgCard,
                RoundedCornerShape(20.dp)
            )
            .border(1.dp, if (selected) GV.Green500 else GV.Divider, RoundedCornerShape(20.dp))
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(label, color = if (selected) GV.White else GV.TextSec, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun StatCard(modifier: Modifier, value: String, label: String, valueColor: Color) {
    Card(
        modifier, shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = GV.BgCard),
        border = BorderStroke(0.5.dp, GV.Divider)
    ) {
        Column(Modifier.padding(vertical = 8.dp, horizontal = 6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, color = valueColor, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(label, color = GV.TextMuted, fontSize = 9.sp, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text, color = GV.Green300, fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 6.dp, top = 4.dp)
    )
}

@Composable
fun InfoRow(icon: String, text: String) {
    Row(Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.Top) {
        Text(icon, fontSize = 13.sp, modifier = Modifier.width(22.dp))
        Text(text, color = GV.TextSec, fontSize = 12.sp, lineHeight = 16.sp)
    }
}

@Composable
fun DetailField(modifier: Modifier, label: String, value: String) {
    Column(modifier) {
        Text(label, color = GV.TextMuted, fontSize = 10.sp)
        Text(value, color = GV.TextPrim, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun MiniChip(text: String, color: Color) {
    Box(
        Modifier.background(color.copy(alpha = 0.12f), RoundedCornerShape(6.dp))
            .border(0.5.dp, color.copy(alpha = 0.35f), RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(text, color = color, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
    }
}