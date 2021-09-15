package pl.agh.edu.erasmus_system.service;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;
import pl.agh.edu.erasmus_system.model.Registration;
import pl.agh.edu.erasmus_system.model.Student;
import pl.agh.edu.erasmus_system.repository.RegistrationRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GeneratePdfFileService {

    private static final String PDF_RESOURCES = "/templates/";

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private SpringTemplateEngine templateEngine;


    public File generatePdf(long editionId) throws IOException, DocumentException {
        Context context = getContext(editionId);
        String html = loadAndFillTemplate(context);
        return renderPdf(html);
    }

    private File renderPdf(String html) throws IOException, DocumentException {
        File file = File.createTempFile("students", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 10);
        renderer.getFontResolver().addFont("fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }

    private Context getContext(long editionId) {
        Context context = new Context();
        List<Registration> acceptedRegistrations = registrationRepository.findAllByContract_Edition_IdAndIsAccepted(editionId);
        System.out.println(acceptedRegistrations.size());
        Map<Student, String> acceptedStudents = acceptedRegistrations.stream()
                .collect(Collectors.toMap(Registration::getStudent, registration -> registration.getContract().getErasmusCode() +
                        " (" +registration.getContract().getContractsCoordinator().getCode() + ")"));
        context.setVariable("students", acceptedStudents);
        return context;
    }

    private String loadAndFillTemplate(Context context) {
        return templateEngine.process("result_pdf_file", context);
    }

}
