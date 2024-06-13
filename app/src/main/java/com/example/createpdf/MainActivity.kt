package com.example.createpdf

//import com.itextpdf.kernel.pdf.PdfWriter
//import com.itextpdf.layout.Document
//import com.itextpdf.layout.element.Paragraph

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_AUDIO
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Image
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {
    // on below line we are creating
    // a variable for our image view.
    lateinit var generatePDFBtn: Button

    // declaring width and height
    // for our PDF file.
    var pageHeight = 1120
    var pageWidth = 792

    // creating a bitmap variable
    // for storing our images
    lateinit var bmp: Bitmap
    lateinit var scaledbmp: Bitmap

    // on below line we are creating a
    // constant code for runtime permissions.
    var PERMISSION_CODE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // on below line we are initializing our button with its id.
        generatePDFBtn = findViewById(R.id.idBtnGeneratePdf)

        // on below line we are initializing our bitmap and scaled bitmap.
        bmp = BitmapFactory.decodeResource(resources, R.drawable.kalash)
        scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false)

        // on below line we are checking permission

        if (!checkPermissions()) {
            requestPermission()
        }
        // on below line we are adding on click listener for our generate button.
        generatePDFBtn.setOnClickListener {
            // on below line we are calling generate
            // PDF method to generate our PDF file.
            //generatePDF()
            //generatePDF1()
            generatePDF2()
        }
    }

    private fun generatePDF2() {
        // Assuming you have set permissions and path for saving the PDF
        val document = Document()
      //  val filePath = "/path/to/your/directory/quotation.pdf"
        val pdfPath = "sample_pdf.pdf"
        val path = this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath
        val file: File = File(path, pdfPath)
        try {
            PdfWriter.getInstance(document, FileOutputStream(file))
            document.open()

            // Convert the drawable resource to Bitmap
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.kalash)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream)
            val imgSbiLogo = Image.getInstance(stream.toByteArray())
            imgSbiLogo.alignment = Image.LEFT
            imgSbiLogo.scaleToFit(80f, 50f)

            val paraImgLogo = Paragraph("")
            paraImgLogo.add(imgSbiLogo)

            val paraImgLogoAfterSpace1 = Paragraph(" ")

            document.add(paraImgLogo)
            document.add(LineSeparator())
            document.add(paraImgLogoAfterSpace1)

            val headerBold = Font(Font.FontFamily.TIMES_ROMAN, 14f, Font.BOLD, BaseColor.WHITE)
            val Para_Header = Paragraph(
                "Premium Quotation for SBI LIFE - Rinn Raksha (UIN: 111N078V03)",
                headerBold
            )

            val headerTable = PdfPTable(1)
            headerTable.widthPercentage = 100f
            val c1 = PdfPCell(Phrase(Para_Header))
            c1.backgroundColor = BaseColor.DARK_GRAY
            c1.backgroundColor = BaseColor.DARK_GRAY
            /*c1.paddingTop = 5f
            c1.paddingLeft = 5f
            c1.paddingRight = 5f
            c1.paddingBottom = 5f*/
            c1.setPadding(5f)
            c1.horizontalAlignment = Element.ALIGN_CENTER
            headerTable.addCell(c1)
            headerTable.horizontalAlignment = Element.ALIGN_CENTER

            val smallBoldForName = Font(Font.FontFamily.TIMES_ROMAN, 12f, Font.BOLD)
            val smallBold = Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD)
            val smallNormal = Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.NORMAL)
            val smallBold1 = Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLDITALIC)

            val paraAddress = Paragraph(
                "SBI Life Insurance Co. Ltd\nRegistered & Corporate Office: 'Natraj', M.V. Road and Western Express, Highway Junction, Andheri (East),Mumbai  400069. IRDAI Registration  No. 111",
                smallBoldForName
            )
            paraAddress.alignment = Element.ALIGN_CENTER

            val paraAddress1 = Paragraph(
                "Website: www.sbilife.co.in | Email: info@sbilife.co.in | CIN: L99999MH2000PLC129113. Toll Free: 1800 267 9090 (Between 9.00 am & 9.00 pm)",
                smallBold
            )
            paraAddress1.alignment = Element.ALIGN_CENTER

            document.add(paraAddress)
            document.add(paraAddress1)
            document.add(paraImgLogoAfterSpace1)
            document.add(headerTable)
            document.add(paraImgLogoAfterSpace1)
            document.add(paraImgLogoAfterSpace1)

            val tableProposerName = PdfPTable(4)
            tableProposerName.widthPercentage = 100f

            val proposalNumberCell1 = PdfPCell(Paragraph("Quotation Number", smallNormal))
            val proposalNumberCell2 = PdfPCell(Paragraph("1234567890", smallBold1))
            proposalNumberCell2.horizontalAlignment = Element.ALIGN_CENTER

            val nameOfProposalCell3 = PdfPCell(Paragraph("Proposer Name ", smallNormal))
            val nameOfProposalCell4 = PdfPCell(Paragraph("xyz", smallBold1))
            nameOfProposalCell4.horizontalAlignment = Element.ALIGN_CENTER
            nameOfProposalCell4.verticalAlignment = Element.ALIGN_CENTER

            val padding = 5f
           /* proposalNumberCell1.padding = padding
            proposalNumberCell2.padding = padding
            nameOfProposalCell3.padding = padding
            nameOfProposalCell4.padding = padding
*/
            proposalNumberCell1.setPadding(5f)
            proposalNumberCell2.setPadding(5f)
            nameOfProposalCell3.setPadding(5f)
            nameOfProposalCell4.setPadding(5f)


            tableProposerName.addCell(proposalNumberCell1)
            tableProposerName.addCell(proposalNumberCell2)
            tableProposerName.addCell(nameOfProposalCell3)
            tableProposerName.addCell(nameOfProposalCell4)
            document.add(tableProposerName)
            document.add(paraImgLogoAfterSpace1)

            val loanTypeTable = PdfPTable(4)
            loanTypeTable.setWidths(floatArrayOf(5f, 5f, 5f, 5f))
            loanTypeTable.widthPercentage = 100f
            loanTypeTable.horizontalAlignment = Element.ALIGN_LEFT

            var cell = PdfPCell(Phrase("Home Loan", smallBold))
            cell.colspan = 4
           // cell.padding = padding
            cell.setPadding(5F)
            cell.backgroundColor = BaseColor.LIGHT_GRAY
            cell.border = Rectangle.BOTTOM or Rectangle.LEFT or Rectangle.RIGHT or Rectangle.TOP
            cell.horizontalAlignment = Element.ALIGN_CENTER
            loanTypeTable.addCell(cell)

            cell = PdfPCell(Phrase("Loan Type", smallNormal))
            cell.horizontalAlignment = Element.ALIGN_LEFT
            //cell.padding = padding
            cell.setPadding(5F)
            loanTypeTable.addCell(cell)

            cell = PdfPCell(Phrase("Home Loan", smallNormal))
            cell.horizontalAlignment = Element.ALIGN_LEFT
            //cell.padding = padding
            cell.setPadding(5F)
            loanTypeTable.addCell(cell)

            cell = PdfPCell(Phrase("Staff/Non-Staff ", smallNormal))
            cell.horizontalAlignment = Element.ALIGN_LEFT
            //cell.padding = padding
            cell.setPadding(5F)
            loanTypeTable.addCell(cell)

            cell = PdfPCell(Phrase("Staff", smallNormal))
            cell.horizontalAlignment = Element.ALIGN_LEFT
            //cell.padding = padding
            cell.setPadding(5F)
            loanTypeTable.addCell(cell)

            document.add(loanTypeTable)
            document.add(paraImgLogoAfterSpace1)
            document.add(paraImgLogoAfterSpace1)

            val finalTable = PdfPTable(2)
            finalTable.setWidths(floatArrayOf(6f, 6f))
            finalTable.widthPercentage = 100f
            finalTable.horizontalAlignment = Element.ALIGN_LEFT

            cell = PdfPCell(Phrase("Premium Installment", smallBold))
            cell.colspan = 2
            //cell.padding = padding
            cell.setPadding(5F)
            cell.backgroundColor = BaseColor.LIGHT_GRAY
            cell.border = Rectangle.BOTTOM or Rectangle.LEFT or Rectangle.RIGHT or Rectangle.TOP
            cell.horizontalAlignment = Element.ALIGN_CENTER
            finalTable.addCell(cell)

            cell = PdfPCell(Phrase("Premium inclusive of Applicable Tax payable for all borrowers (Rs.)", smallNormal))
            cell.horizontalAlignment = Element.ALIGN_LEFT
    //        cell.padding = padding
            cell.setPadding(5F)
            finalTable.addCell(cell)

            cell = PdfPCell(Phrase("37247", smallNormal))
            cell.horizontalAlignment = Element.ALIGN_LEFT
//            cell.padding = padding
            cell.setPadding(5F)
            finalTable.addCell(cell)

            cell = PdfPCell(Phrase("Applicable Tax (Rs.)", smallNormal))
            cell.horizontalAlignment = Element.ALIGN_LEFT
//            cell.padding = padding
            cell.setPadding(5F)
            finalTable.addCell(cell)

            cell = PdfPCell(Phrase("3826484", smallNormal))
            cell.horizontalAlignment = Element.ALIGN_LEFT
//            cell.padding = padding
            cell.setPadding(5F)
            finalTable.addCell(cell)

            cell = PdfPCell(Phrase("Premium inclusive of Applicable Tax payable for all borrowers (Rs.)", smallNormal))
            cell.horizontalAlignment = Element.ALIGN_LEFT
//            cell.padding = padding
            cell.setPadding(5F)
            finalTable.addCell(cell)

            cell = PdfPCell(Phrase("82648326", smallNormal))
            cell.horizontalAlignment = Element.ALIGN_LEFT
//            cell.padding = padding
            cell.setPadding(5F)
            finalTable.addCell(cell)

            document.add(finalTable)
            document.add(paraImgLogoAfterSpace1)
            document.add(paraImgLogoAfterSpace1)


            /////////////////////
            val small_bold = Font(
                Font.FontFamily.TIMES_ROMAN, 8f,
                Font.BOLD
            )
            val small_bold_for_name = Font(
                Font.FontFamily.TIMES_ROMAN,
                10f, Font.BOLD
            )
            val small_bold1 = Font(
                Font.FontFamily.TIMES_ROMAN, 6f,
                Font.BOLD
            )

            val small_normal = Font(
                Font.FontFamily.TIMES_ROMAN, 8f,
                Font.NORMAL
            )

            val sub_headerBold = Font(Font.FontFamily.TIMES_ROMAN, 7f, Font.BOLD)
            val small_normal2 = Font(
                Font.FontFamily.TIMES_ROMAN, 6f,
                Font.NORMAL
            )
            val Para_health_Header = Paragraph()
            Para_health_Header
                .add(
                    Paragraph(
                        "5. MEDICAL QUESTIONNAIRE:- In case where insurance is proposed on Minor Life, the answers should relate to medical status of Minor Life to be Assured",
                        sub_headerBold
                    )
                )

            val health_headertable = PdfPTable(1)
            health_headertable.spacingBefore = 4f
            health_headertable.widthPercentage = 100f
            val health_c1 = PdfPCell(Phrase(Para_health_Header))
            health_c1.backgroundColor = BaseColor.LIGHT_GRAY
            health_c1.setPadding(5f)
            health_c1.horizontalAlignment = Element.ALIGN_CENTER
            health_headertable.addCell(health_c1)
            health_headertable.horizontalAlignment = Element.ALIGN_CENTER
            document.add(health_headertable)

            val row_tag = PdfPTable(1)
            row_tag.widthPercentage = 100f
            val tag_cell = PdfPCell(
                Paragraph(
                    "Important :Please read this section fully and give correct details.",
                    small_normal2
                )
            )
            tag_cell.setPadding(5f)
            row_tag.addCell(tag_cell)
            document.add(row_tag)

            // if (productCode.Equals(BLC.ProductString.sbi_life_rinn_raksha))
            // {

            // if (productCode.Equals(BLC.ProductString.sbi_life_rinn_raksha))
            // {
            val height_pdf = DecimalFormat("##.##").format("0.0".trim { it <= ' ' }.toDouble())
            val row1 = PdfPTable(4)
            row1.widthPercentage = 100f
            val row1_cell_1 = PdfPCell(
                Paragraph(
                    "Height", small_normal
                )
            )
            // String height_pdf = "";
            // String height_pdf = "";
            val row1_cell_2 = PdfPCell(
                Paragraph(
                    height_pdf
                            + " Cms", small_bold
                )
            )

            row1_cell_2.horizontalAlignment = Element.ALIGN_CENTER
            row1_cell_2.verticalAlignment = Element.ALIGN_CENTER
            val row1_cell_3 = PdfPCell(
                Paragraph(
                    "Weight",
                    small_normal
                )
            )

            val row1_cell_4 = PdfPCell(
                Paragraph(
                    ("v"
                            + " Kgs"), small_bold
                )
            )

            row1_cell_4.horizontalAlignment = Element.ALIGN_CENTER
            row1_cell_4.verticalAlignment = Element.ALIGN_CENTER
            row1_cell_1.setPadding(5f)
            row1_cell_2.setPadding(5f)
            row1_cell_3.setPadding(5f)
            row1_cell_4.setPadding(5f)

            row1.addCell(row1_cell_1)
            row1.addCell(row1_cell_2)
            row1.addCell(row1_cell_3)
            row1.addCell(row1_cell_4)
            document.add(row1)

            val mh_row1 = PdfPTable(2)
            mh_row1.widthPercentage = 100f
            val mh_row1_cell_1 = PdfPCell(
                Paragraph(
                    "i. Have you consulted any doctor for surgical operations or have been hospitalised for any disorder other than minor cough,cold or flu during the last 5 years?",
                    small_normal
                )
            )
            mh_row1_cell_1.setPadding(5f)
            val mh_row1_cell_2 = PdfPCell(
                Paragraph(
                    "v", small_bold
                )
            )
            mh_row1_cell_2.horizontalAlignment = Element.ALIGN_CENTER
            mh_row1_cell_2.verticalAlignment = Element.ALIGN_CENTER
            mh_row1_cell_2.setPadding(5f)
            mh_row1.setWidths(floatArrayOf(0.8f, 0.2f))
            mh_row1.addCell(mh_row1_cell_1)
            mh_row1.addCell(mh_row1_cell_2)
            document.add(mh_row1)
            // }
            // medical history row2_1

            // }
            // medical history row2_1
            val mh_row2_1 = PdfPTable(2)
            mh_row2_1.widthPercentage = 100f
            val mh_row2_1_cell_1 = PdfPCell(
                Paragraph(
                    "ii. Have you ever had any illness/injury, major surgical operation or received any treatment for any medical condition for a continuous period of more than 14 days? (Except for minor cough, cold, flu, appendicitis & typhoid)",
                    small_normal
                )
            )
            mh_row2_1_cell_1.setPadding(5f)
            val mh_row2_1_cell_2 = PdfPCell(
                Paragraph(
                    "v", small_bold
                )
            )
            mh_row2_1_cell_2.horizontalAlignment = Element.ALIGN_CENTER
            mh_row2_1_cell_2.verticalAlignment = Element.ALIGN_CENTER
            mh_row2_1_cell_2.setPadding(5f)
            mh_row2_1.setWidths(floatArrayOf(0.8f, 0.2f))
            mh_row2_1.addCell(mh_row2_1_cell_1)
            mh_row2_1.addCell(mh_row2_1_cell_2)
            document.add(mh_row2_1)

            // medical history row3

            // medical history row3
            val mh_row3 = PdfPTable(1)
            mh_row3.widthPercentage = 100f
            val mh_row3_cell_1 = PdfPCell(
                Paragraph(
                    "iii. Have you ever suffered from / been treated / hospitalized for or diagnosed to have -",
                    small_normal
                )
            )
            mh_row3_cell_1.setPadding(5f)
            mh_row3.addCell(mh_row3_cell_1)
            document.add(mh_row3)
            // medical history row4

            // medical history row4
            val mh_row4 = PdfPTable(4)
            mh_row4.widthPercentage = 100f

            val mh_row4_cell_1 = PdfPCell(
                Paragraph(
                    "(a) Diabetes, raised blood sugar or high blood pressure",
                    small_normal
                )
            )
            mh_row4_cell_1.setPadding(5f)

            val mh_row4_cell_2 = PdfPCell(
                Paragraph(
                    "v", small_bold
                )
            )
            mh_row4_cell_2.setPadding(5f)
            mh_row4_cell_2.horizontalAlignment = Element.ALIGN_CENTER

            val mh_row4_cell_3 = PdfPCell(
                Paragraph(
                    "(b) Chest pain, heart attack, heart disease or any other disorder of the circulatory system.",
                    small_normal
                )
            )
            mh_row4_cell_3.setPadding(5f)

            val mh_row4_cell_4 = PdfPCell(
                Paragraph(
                    "v", small_bold
                )
            )
            mh_row4_cell_4.setPadding(5f)
            mh_row4_cell_4.horizontalAlignment = Element.ALIGN_CENTER

            mh_row4.addCell(mh_row4_cell_1)
            mh_row4.addCell(mh_row4_cell_2)
            mh_row4.addCell(mh_row4_cell_3)
            mh_row4.addCell(mh_row4_cell_4)
            document.add(mh_row4)

            // medical history row5


            // medical history row5
            val mh_row5 = PdfPTable(4)
            mh_row5.widthPercentage = 100f
            val mh_row5_cell_1 = PdfPCell(
                Paragraph(
                    "(c) Stroke, paralysis, disorder of the brain/nervous system.",
                    small_normal
                )
            )
            mh_row5_cell_1.setPadding(5f)
            val mh_row5_cell_2 = PdfPCell(
                Paragraph(
                    "value", small_bold
                )
            )
            mh_row5_cell_2.setPadding(5f)
            mh_row5_cell_2.horizontalAlignment = Element.ALIGN_CENTER
            val mh_row5_cell_3 = PdfPCell(
                Paragraph(
                    "(d) HIV infection, AIDS", small_normal
                )
            )
            mh_row5_cell_3.setPadding(5f)
            val mh_row5_cell_4 = PdfPCell(
                Paragraph(
                    "value", small_bold
                )
            )
            mh_row5_cell_4.setPadding(5f)
            mh_row5_cell_4.horizontalAlignment = Element.ALIGN_CENTER
            mh_row5.addCell(mh_row5_cell_1)
            mh_row5.addCell(mh_row5_cell_2)
            mh_row5.addCell(mh_row5_cell_3)
            mh_row5.addCell(mh_row5_cell_4)
            document.add(mh_row5)

            // medical history row6

            // medical history row6
            val mh_row6 = PdfPTable(4)
            mh_row6.widthPercentage = 100f
            val mh_row6_cell_1 = PdfPCell(
                Paragraph(
                    "(e) Cancer, tumor, growth or cyst of any kind",
                    small_normal
                )
            )
            mh_row6_cell_1.setPadding(5f)
            val mh_row6_cell_2 = PdfPCell(
                Paragraph(
                    "value", small_bold
                )
            )
            mh_row6_cell_2.setPadding(5f)
            mh_row6_cell_2.horizontalAlignment = Element.ALIGN_CENTER
            val mh_row6_cell_3 = PdfPCell(
                Paragraph(
                    "(f) Any genitourinary or kidney disorder, Hepatitis B/C or any other liver disease",
                    small_normal
                )
            )
            mh_row6_cell_3.setPadding(5f)
            val mh_row6_cell_4 = PdfPCell(
                Paragraph(
                    "value", small_bold
                )
            )
            mh_row6_cell_4.setPadding(5f)
            mh_row6_cell_4.horizontalAlignment = Element.ALIGN_CENTER
            mh_row6.addCell(mh_row6_cell_1)
            mh_row6.addCell(mh_row6_cell_2)
            mh_row6.addCell(mh_row6_cell_3)
            mh_row6.addCell(mh_row6_cell_4)
            document.add(mh_row6)

            // medical history row7

            // medical history row7
            val mh_row7 = PdfPTable(2)
            mh_row7.widthPercentage = 100f
            val mh_row7_cell_1 = PdfPCell(
                Paragraph(
                    "(g) Any digestive disorder (ulcer, colitis etc), any disease of the gall bladder, spleen, any blood disorder, disorder of any other gland (e.g. Thyroid etc) or any musculoskeletal disorder",
                    small_normal
                )
            )
            mh_row7_cell_1.setPadding(5f)
            val mh_row7_cell_2 = PdfPCell(
                Paragraph(
                    "value", small_bold
                )
            )
            mh_row7_cell_2.horizontalAlignment = Element.ALIGN_CENTER
            mh_row7_cell_2.verticalAlignment = Element.ALIGN_CENTER
            mh_row7_cell_2.setPadding(5f)
            mh_row7.setWidths(floatArrayOf(0.8f, 0.2f))
            mh_row7.addCell(mh_row7_cell_1)
            mh_row7.addCell(mh_row7_cell_2)
            document.add(mh_row7)

            // medical history row8


            // medical history row8
            val mh_row8 = PdfPTable(4)
            mh_row8.widthPercentage = 100f
            val mh_row8_cell_1 = PdfPCell(
                Paragraph(
                    "(h) Asthma, Tuberculosis, Pneumonia, or any other disease of the lung.",
                    small_normal
                )
            )
            mh_row8_cell_1.setPadding(5f)
            val mh_row8_cell_2 = PdfPCell(
                Paragraph(
                    "value", small_bold
                )
            )
            mh_row8_cell_2.setPadding(5f)
            mh_row8_cell_2.horizontalAlignment = Element.ALIGN_CENTER
            val mh_row8_cell_3 = PdfPCell(
                Paragraph(
                    "(i) Mental, psychiatric or nervous disorder",
                    small_normal
                )
            )
            mh_row8_cell_3.setPadding(5f)
            val mh_row8_cell_4 = PdfPCell(
                Paragraph(
                    "value", small_bold
                )
            )
            mh_row8_cell_4.setPadding(5f)
            mh_row8_cell_4.horizontalAlignment = Element.ALIGN_CENTER
            mh_row8.addCell(mh_row8_cell_1)
            mh_row8.addCell(mh_row8_cell_2)
            mh_row8.addCell(mh_row8_cell_3)
            mh_row8.addCell(mh_row8_cell_4)
            document.add(mh_row8)

            val mh_row9 = PdfPTable(2)
            mh_row9.widthPercentage = 100f
            val mh_row9_cell_1 = PdfPCell(
                Paragraph(
                    "(iv) Have you suffered from any other disease not mentioned above?",
                    small_normal
                )
            )
            mh_row9_cell_1.setPadding(5f)
            val mh_row9_cell_2 = PdfPCell(
                Paragraph(
                    "value", small_bold
                )
            )
            mh_row9_cell_2.horizontalAlignment = Element.ALIGN_CENTER
            mh_row9_cell_2.verticalAlignment = Element.ALIGN_CENTER
            mh_row9_cell_2.setPadding(5f)
            mh_row9.setWidths(floatArrayOf(0.8f, 0.2f))
            mh_row9.addCell(mh_row9_cell_1)
            mh_row9.addCell(mh_row9_cell_2)
            document.add(mh_row9)

            val mh_row10 = PdfPTable(2)
            mh_row10.widthPercentage = 100f
            val mh_row10_cell_1 = PdfPCell(
                Paragraph(
                    "(v) Are you at present taking any medication, or on any special diet or on any treatment?",
                    small_normal
                )
            )
            mh_row10_cell_1.setPadding(5f)
            val mh_row10_cell_2 = PdfPCell(
                Paragraph(
                    "value", small_bold
                )
            )
            mh_row10_cell_2.horizontalAlignment = Element.ALIGN_CENTER
            mh_row10_cell_2.verticalAlignment = Element.ALIGN_CENTER
            mh_row10_cell_2.setPadding(5f)
            mh_row10.setWidths(floatArrayOf(0.8f, 0.2f))
            mh_row10.addCell(mh_row10_cell_1)
            mh_row10.addCell(mh_row10_cell_2)
            document.add(mh_row10)

            val mh_row11 = PdfPTable(2)
            mh_row11.widthPercentage = 100f
            val mh_row11_cell_1 = PdfPCell(
                Paragraph(
                    "(vi) Has a proposal for Life Insurance, ever been declined, postponed, withdrawn or accepted at extra premium?",
                    small_normal
                )
            )
            mh_row11_cell_1.setPadding(5f)
            val mh_row11_cell_2 = PdfPCell(
                Paragraph(
                    "value", small_bold
                )
            )
            mh_row11_cell_2.horizontalAlignment = Element.ALIGN_CENTER
            mh_row11_cell_2.verticalAlignment = Element.ALIGN_CENTER
            mh_row11_cell_2.setPadding(5f)
            mh_row11.setWidths(floatArrayOf(0.8f, 0.2f))
            mh_row11.addCell(mh_row11_cell_1)
            mh_row11.addCell(mh_row11_cell_2)
            document.add(mh_row11)

            val mh_row12 = PdfPTable(2)
            mh_row12.widthPercentage = 100f
            val mh_row12_cell_1 = PdfPCell(
                Paragraph(
                    "(vii) Have you had or have been advised to undergo any of the following tests or investigations?",
                    small_normal
                )
            )
            mh_row12_cell_1.setPadding(5f)
            val mh_row12_cell_2 = PdfPCell(
                Paragraph(
                    "value", small_bold
                )
            )
            mh_row12_cell_2.horizontalAlignment = Element.ALIGN_CENTER
            mh_row12_cell_2.verticalAlignment = Element.ALIGN_CENTER
            mh_row12_cell_2.setPadding(5f)
            mh_row12.setWidths(floatArrayOf(0.8f, 0.2f))
            mh_row12.addCell(mh_row12_cell_1)
            mh_row12.addCell(mh_row12_cell_2)
            document.add(mh_row12)

            val Advc_test_ivstgtn_rinnraksha = PdfPTable(2)
            Advc_test_ivstgtn_rinnraksha.widthPercentage = 100f
            val Advc_test_ivstgtn_rinnraksha_cell1 = PdfPCell(
                Paragraph(
                    "(IF Yes, Please Select which of the following and provide reasons for undergoing the tests) Ultra Sonography,CT Scan/MRI,Biopsy,Coronary Angiography ",
                    small_normal
                )
            )
            val str_test = "NA"
            val Advc_test_ivstgtn_rinnraksha_cell2 = PdfPCell(
                Paragraph(str_test, small_bold)
            )
            Advc_test_ivstgtn_rinnraksha_cell1.setPadding(5f)
            Advc_test_ivstgtn_rinnraksha_cell2.setPadding(5f)
            Advc_test_ivstgtn_rinnraksha_cell2.horizontalAlignment = Element.ALIGN_CENTER
            Advc_test_ivstgtn_rinnraksha
                .addCell(Advc_test_ivstgtn_rinnraksha_cell1)
            Advc_test_ivstgtn_rinnraksha
                .addCell(Advc_test_ivstgtn_rinnraksha_cell2)
            document.add(Advc_test_ivstgtn_rinnraksha)

            ////
            val BI_Pdftable_proposer_signature211 = PdfPTable(3)
            BI_Pdftable_proposer_signature211.widthPercentage = 100f
            BI_Pdftable_proposer_signature211.spacingBefore = 4f
            val BI_Pdftable_proposer_signature2_cell111 = PdfPCell(
                Paragraph(
                    "Signature of Member or Proposer(In case Member is a Minor)" + "\n"
                            + "This document is eSigned by "
                            + "xyz",
                    small_normal
                )
            )
            BI_Pdftable_proposer_signature2_cell111.horizontalAlignment = Element.ALIGN_CENTER
            BI_Pdftable_proposer_signature2_cell111.verticalAlignment = Element.ALIGN_CENTER
            val BI_Pdftable_proposer_signature2_cell211 = PdfPCell()
            BI_Pdftable_proposer_signature2_cell211.fixedHeight = 60f

            // added by me

            // added by me
            val BI_Pdftable_proposer_signature2_cell311 = PdfPCell(
                Paragraph("", small_normal)
            )

            BI_Pdftable_proposer_signature2_cell311.horizontalAlignment = Element.ALIGN_CENTER
            BI_Pdftable_proposer_signature2_cell311.verticalAlignment = Element.ALIGN_CENTER

            // end added by me

            // end added by me
            val str_sign_of_proposer: String = ""
            var image_terms_condition_Proposer211: Image? = null
            if ((str_sign_of_proposer != ""
                        && (str_sign_of_proposer != null))
            ) {
                val fbyt_TCProposerSign11 = Base64.decode(
                    str_sign_of_proposer, 0
                )
                val Proposerbitmap11 = BitmapFactory.decodeByteArray(
                    fbyt_TCProposerSign11, 0, fbyt_TCProposerSign11.size
                )
                val temrs_conditons_Proposer_stream11 = ByteArrayOutputStream()
                (Proposerbitmap11).compress(
                    Bitmap.CompressFormat.PNG, 50,
                    temrs_conditons_Proposer_stream11
                )
                image_terms_condition_Proposer211 = Image
                    .getInstance(
                        temrs_conditons_Proposer_stream11
                            .toByteArray()
                    )
                BI_Pdftable_proposer_signature2_cell211.image = image_terms_condition_Proposer211
            }
            BI_Pdftable_proposer_signature211
                .addCell(BI_Pdftable_proposer_signature2_cell111)
            BI_Pdftable_proposer_signature211
                .addCell(BI_Pdftable_proposer_signature2_cell211)

            // added by me

            // added by me
            val photo: String = ""
            var photoImage11: Image? = null
            if (photo != "" && photo != null) {
                val Photo_stream11 = ByteArrayOutputStream()
                val fbyt_Proposer_photo11 = Base64.decode(photo, 0)
                val Cr_sr_signbitmap11 = BitmapFactory.decodeByteArray(
                    fbyt_Proposer_photo11, 0, fbyt_Proposer_photo11.size
                )
                (Cr_sr_signbitmap11).compress(
                    Bitmap.CompressFormat.JPEG, 50,
                    Photo_stream11
                )
                photoImage11 = Image.getInstance(Photo_stream11.toByteArray())
                /*
             * photoImage11.setAlignment(Image.RIGHT);
             * photoImage11.scaleToFit(200, 90); document.add(photoImage11);
             */BI_Pdftable_proposer_signature2_cell311.image = photoImage11
            }

            BI_Pdftable_proposer_signature211
                .addCell(BI_Pdftable_proposer_signature2_cell311)
            // end addded by me

            // end addded by me
            document.add(BI_Pdftable_proposer_signature211)


        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            document.close()
        }
    }

/*
    private fun generatePDF1() {
        val pdfPath = "sample_table.pdf"
        val path = this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath
        val file: File = File(path, pdfPath)

        //val file = File(pdfPath)

        // Initialize PDF writer and document
        val pdfWriter = PdfWriter(file)
        val pdfDocument = com.itextpdf.kernel.pdf.PdfDocument(pdfWriter)
        val document = Document(pdfDocument)
        val header = Paragraph("Your Header Text Here")
        header.setFontSize(14f) // Adjust font size as needed
        header.setMarginTop(20f) // Adjust margin as needed
            .setTextAlignment(TextAlignment.CENTER)
            .setBold()
        // Create a table with 3 columns
        val table = Table(floatArrayOf(1f, 1f, 1f))
        //table.addHeaderCell("Kotli Pdf")
        table.setWidth(UnitValue.createPercentValue(100f))

        // Define the box style
        val boxColor: DeviceRgb = DeviceRgb(0, 150, 0) // Green color for the boxes
        val boxBorderColor: DeviceRgb = DeviceRgb(0, 0, 0) // Black border color

        // Add table headers
        table.setHorizontalAlignment(HorizontalAlignment.CENTER)
        //table.setWidth(100f)

        table.addHeaderCell(
            Cell().add(Paragraph("Column 1")).setTextAlignment(TextAlignment.CENTER)
        )
        table.addHeaderCell(
            Cell().add(Paragraph("Column 2")).setTextAlignment(TextAlignment.CENTER)
        )
        table.addHeaderCell(
            Cell().add(Paragraph("Column 3")).setTextAlignment(TextAlignment.CENTER)
        )

        // Add rows with boxes as content
        */
/*for (i in 1..5) {
            table.addCell(createBoxCell("Box $i", boxColor, boxBorderColor))
            table.addCell(createBoxCell("Box ${i + 5}", boxColor, boxBorderColor))
            table.addCell(createBoxCell("Box ${i + 10}", boxColor, boxBorderColor))
        }*//*

        table.addCell(createBoxCell("Box 1", boxColor, boxBorderColor))
        table.addCell(createBoxCell("Box 2", boxColor, boxBorderColor))
        table.addCell(createBoxCell("Box 3", boxColor, boxBorderColor))

        table.addCell(createBoxCell("Box 11", boxColor, boxBorderColor))
        table.addCell(createBoxCell("Box 22", boxColor, boxBorderColor))
        table.addCell(createBoxCell("Box 33", boxColor, boxBorderColor))
        //cell.add(Paragraph("boxx").setTextAlignment(TextAlignment.CENTER))
        // cell.setBackgroundColor(boxBorderColor)
        // Add the table to the document
        document.add(header)
        document.add(table)
        Toast.makeText(applicationContext, "PDF file generated..", Toast.LENGTH_SHORT).show()

        // Close the document
        document.close()
    }
*/

    // on below line we are creating a generate PDF method
    // which is use to generate our PDF file.
    fun generatePDF() {
        // creating an object variable
        // for our PDF document.
        var pdfDocument: PdfDocument = PdfDocument()

        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        var paint: Paint = Paint()
        var title: Paint = Paint()

        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        var myPageInfo: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

        // below line is used for setting
        // start page for our PDF file.
        var myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)

        // creating a variable for canvas
        // from our page of PDF.
        var canvas: Canvas = myPage.canvas

        // below line is used to draw our image on our PDF file.
        // the first parameter of our drawbitmap method is
        // our bitmap
        // second parameter is position from left
        // third parameter is position from top and last
        // one is our variable for paint.
        canvas.drawBitmap(scaledbmp, 56F, 40F, paint)

        // below line is used for adding typeface for
        // our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))

        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.textSize = 15F

        // below line is sued for setting color
        // of our text inside our PDF file.
        title.setColor(ContextCompat.getColor(this, R.color.purple_200))

        // below line is used to draw text in our PDF file.
        // the first parameter is our text, second parameter
        // is position from start, third parameter is position from top
        // and then we are passing our variable of paint which is title.
        canvas.drawText("A portal for IT professionals.", 209F, 100F, title)
        canvas.drawText("Geeks for Geeks", 209F, 80F, title)
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        title.setColor(ContextCompat.getColor(this, R.color.purple_200))
        title.textSize = 15F

        // below line is used for setting
        // our text to center of PDF.
        title.textAlign = Paint.Align.CENTER
        canvas.drawText("This is sample document which we have created.", 396F, 560F, title)

        // after adding all attributes to our
        // PDF file we will be finishing our page.
        pdfDocument.finishPage(myPage)

        // below line is used to set the name of
        // our PDF file and its path.
        val path = this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath
        val file: File = File(path, "GFG.pdf")

        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(FileOutputStream(file))

            // on below line we are displaying a toast message as PDF file generated..
            Toast.makeText(applicationContext, "PDF file generated..", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // below line is used
            // to handle error
            e.printStackTrace()

            // on below line we are displaying a toast message as fail to generate PDF
            Toast.makeText(applicationContext, "Fail to generate PDF file..", Toast.LENGTH_SHORT)
                .show()
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close()
    }

    /*fun createBoxCell(content: String, backgroundColor: DeviceRgb, borderColor: DeviceRgb): Cell {
        val cell = Cell()
        cell.add(Paragraph(content).setTextAlignment(TextAlignment.CENTER))
        cell.setBackgroundColor(backgroundColor)
        return cell
    }*/

    private fun checkPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val readImagesPermission = ContextCompat.checkSelfPermission(this, READ_MEDIA_IMAGES)
            val readAudioPermission = ContextCompat.checkSelfPermission(this, READ_MEDIA_AUDIO)
            val readVideoPermission = ContextCompat.checkSelfPermission(this, READ_MEDIA_VIDEO)
            readImagesPermission == PackageManager.PERMISSION_GRANTED &&
                    readVideoPermission == PackageManager.PERMISSION_GRANTED &&
                    readAudioPermission == PackageManager.PERMISSION_GRANTED
        } else {
            val readPermission = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
            readPermission == PackageManager.PERMISSION_GRANTED
        }
    }


    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionsLauncher.launch(
                arrayOf(
                    READ_MEDIA_IMAGES,
                    READ_MEDIA_VIDEO,
                    READ_MEDIA_AUDIO
                )
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(READ_EXTERNAL_STORAGE),
                PERMISSION_CODE
            )
        }
    }

    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied..", Toast.LENGTH_SHORT).show()
            }
        }
    }
}