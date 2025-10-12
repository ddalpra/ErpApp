package it.ddalpra.acme.erpApp.item.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.ddalpra.acme.erpApp.item.client.ItemApiClient;
import it.ddalpra.acme.erpApp.item.entity.Item;
import it.ddalpra.acme.erpApp.item.entity.ItemStatus;
import it.ddalpra.acme.erpApp.item.entity.UnitOfMeasure;


@Controller
@RequestMapping("/items") // All methods in this controller will be under /items
public class ItemWebController {

    private final ItemApiClient itemApiClient;

    public ItemWebController(ItemApiClient itemApiClient) {
        this.itemApiClient = itemApiClient;
    }

    /**
     * Mostra la lista di tutti gli articoli.
     */
    @GetMapping
    public String showItemList(Model model) {
        // Chiamata bloccante per semplicità nella UI.
        // In un'app complessa, si potrebbe usare un approccio reattivo completo.
        model.addAttribute("items", itemApiClient.getAllItems().collectList().block());
        return "items/list"; // Ritorna il nome del file HTML (list.html)
    }

    /**
     * Mostra il form per creare un nuovo articolo.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("units", UnitOfMeasure.values()); // Passa le unità di misura al form
        model.addAttribute("itemStatus", ItemStatus.values());
        return "items/form"; // Ritorna il nome del file HTML (form.html)
    }

    /**
     * Mostra il form per modificare un articolo esistente.
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Item item = itemApiClient.getItemById(id).block();
        model.addAttribute("item", item);
        model.addAttribute("units", UnitOfMeasure.values());
        model.addAttribute("itemStatus", ItemStatus.values()); // Aggiungi questo
        return "items/form";
    }

    /**
     * Gestisce il salvataggio (creazione o aggiornamento) di un articolo.
     */
    @PostMapping("/save")
    public String saveItem(@ModelAttribute("item") Item item) {
        if (item.getId() == null) {
            // Crea un nuovo articolo
            itemApiClient.createItem(item).block();
        } else {
            // Aggiorna un articolo esistente
            itemApiClient.updateItem(item.getId(), item).block();
        }
        return "redirect:/items"; // Reindirizza alla lista dopo il salvataggio
    }

    /**
     * Gestisce la cancellazione di un articolo.
     */
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable UUID id) {
        itemApiClient.deleteItem(id).block();
        return "redirect:/items";
    }
}