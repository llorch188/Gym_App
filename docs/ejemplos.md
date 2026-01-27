# :test_tube: Ejemplos de uso

## :pushpin: Ejemplo 1: Rutina de pecho

```python hl_lines="2"
rutina = "Pecho"
ejercicios = ["Press banca", "Aperturas", "Fondos"]
print(rutina, ejercicios)
```

> Anotación: la línea 2 está destacada (hl_lines="2") para llamar la atención sobre la lista de ejercicios.

### :bookmark_tabs: Annotations y explicación
- La función principal de este snippet es mostrar la estructura mínima de una rutina.
- Reemplaza los valores por los reales de tu aplicación al documentar ejemplos prácticos.

### :page_facing_up: Código comentado (ejemplo)
```python
# Crea una plantilla de rutina simple
def crear_plantilla(nombre):
    plantilla = {"rutina": nombre, "dias": []}
    plantilla["dias"].append({"dia": 1, "ejercicios": []})
    return plantilla
```

### Notas y footnotes
Puedes consultar plantillas compartidas por la comunidad[^comunidad].

[^comunidad]: Plantillas creadas por usuarios y compartidas en el foro interno de la app. Asegúrate de adaptar permisos antes de importar plantillas externas.