package it.ddalpra.acme.erpApp.config.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/endpoints")
public class EndpointAdminController {

    private final EndpointConfigurationService endpointService;

    public EndpointAdminController(EndpointConfigurationService endpointService) {
        this.endpointService = endpointService;
    }

    @GetMapping
    public String listEndpoints(Model model) {
        model.addAttribute("endpoints", endpointService.getAllEndpoints());
        return "admin/endpoints/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("endpoint", new WebServiceEndpoint());
        return "admin/endpoints/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        WebServiceEndpoint endpoint = endpointService.getEndpointById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid endpoint Id:" + id));
        model.addAttribute("endpoint", endpoint);
        return "admin/endpoints/form";
    }

    @PostMapping("/save")
    public String saveEndpoint(@ModelAttribute("endpoint") WebServiceEndpoint endpoint) {
        // Semplice validazione per assicurarsi che i campi non siano vuoti
        if (endpoint.getServiceName() == null || endpoint.getServiceName().trim().isEmpty() ||
            endpoint.getBaseUrl() == null || endpoint.getBaseUrl().trim().isEmpty()) {
            // In un'app reale, si userebbe la validazione di Spring (@Valid)
            return "admin/endpoints/form";
        }
        endpointService.saveEndpoint(endpoint);
        return "redirect:/admin/endpoints";
    }

    @GetMapping("/delete/{id}")
    public String deleteEndpoint(@PathVariable Long id) {
        // Verifica se l'endpoint esiste prima di cancellare per evitare errori
        endpointService.getEndpointById(id).ifPresent(endpoint -> {
            endpointService.deleteEndpoint(id);
        });
        return "redirect:/admin/endpoints";
    }
}