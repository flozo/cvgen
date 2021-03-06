#!/usr/bin/env python3
"""Define style-related classes."""

from PyPDF2 import PdfFileReader


class Preamble:
    """Define LaTeX preamble."""

    def __init__(self, documentclass, packages, settings, metadata):
        self.documentclass = documentclass
        self.packages = packages
        self.settings = settings
        self.metadata = metadata

    def generate(self, language):
        """Generate LaTeX code for preamble."""
        # Generate code for documentclass
        dc = list(self.documentclass.items())[0]
        l = ['\\documentclass[{classoptions}]{{{classname}}}'.format(classoptions=dc[1], classname=dc[0])]
        if language == 'en':
            newlang = 'english'
        elif language == 'de':
            newlang = 'german'
        # Generate code for packages
        for key, value in self.packages.items():
            # Overwrite JSON language settings with command-line option
            if key == 'babel':
                oldlang = value
                value = newlang
                print('[language] Overwriting settings ({}) for package {}, due to option --language={}'.format(oldlang, key, language))
            if value != '':
                l.append('\\usepackage[{packageoptions}]{{{packagename}}}'.format(packageoptions=value, packagename=key))
            else:
                l.append('\\usepackage{{{packagename}}}'.format(packagename=key))
        # Generate code for package settings
        for key, value in self.settings.items():
            # Special treatment for hypersetup if metadata has to be included:
            if key == 'hypersetup' and self.metadata is not None:
                l = l + self.include_meta()
            else:
                l.append('\\{name}{{{arguments}}}'.format(name=key, arguments=value))
        return l

    def include_meta(self):
        """
        Include metadata to hypersetup in preamble
        """
        # Store non-meta data hypersetup settings in variable
        hypersetup = self.settings['hypersetup'].split(',')
        # Rewrite LaTeX code for hypersetup including meta and non-meta data
        l = ['\\hypersetup{']
        for item in hypersetup:
            l.append('\t' + item.replace(' ', '') + ',')
        l = l + self.metadata
        l.append('\t}')
        return l


class Sepline:
    """Define separation line."""

    def __init__(self, sepline):
        self.x = sepline['x']
        self.y = sepline['y']
        self.width = sepline['width']
        self.thickness = sepline['thickness']
        self.color = sepline['color']


class Page:
    """Define general page properties."""

    def __init__(self, name, settings):
        self.name = name
        self.width = settings['width']
        self.height = settings['height']
        self.border_top = settings['border_top']
        self.border_bottom = settings['border_bottom']
        self.border_left = settings['border_left']
        self.border_right = settings['border_right']
        self.text_width = self.width-self.border_left-self.border_right
        self.text_height = self.height-self.border_top-self.border_bottom
        self.background_color = settings['background_color']
        self.draft = settings['draft']
        self.draft_highlight_color = settings['draft_highlight_color']
        self.language = settings['language']

    def add_headsepline(self, thickness, color, fullwidth):
        """Generate LaTeX code for header separation line."""
        args = {'x': self.border_left,
                'y': self.height-self.border_top,
                'width': self.text_width,
                'thickness': thickness,
                'color': color}
        if fullwidth is True:
            args['x'] = 0
            args['width'] = self.width
        sepline = Sepline(args)
        return '\\draw [draw={}, line width={}cm] ({}, {}) -- +({}, 0);'.format(sepline.color, sepline.thickness, sepline.x, sepline.y, sepline.width)

    def add_footsepline(self, thickness, color, fullwidth):
        """Generate LaTeX code for footer separation line."""
        args = {'x': self.border_left,
                'y': self.border_bottom,
                'width': self.text_width,
                'thickness': thickness,
                'color': color}
        if fullwidth is True:
            args['x'] = 0
            args['width'] = self.width
        sepline = Sepline(args)
        return '\\draw [draw={}, line width={}cm] ({}, {}) -- +({}, 0);'.format(sepline.color, sepline.thickness, sepline.x, sepline.y, sepline.width)

    def add_title(self, text, fontsize, align, color, yshift):
        """Add page title."""
        return '\\node [anchor=south west, text width={}cm, align={}, font=\\{}, color={}, yshift={}cm] at ({}, {}) {{{}}};'.format(self.text_width, align, fontsize, color, yshift, self.border_left, self.height-self.border_top, text)

    def latex_head(self):
        """Generate LaTeX code for a tikzpicture head."""
        l = [
            '% {}'.format(self.name.upper()),
            '\\begin{tikzpicture}[',
            '\t' + 'inner xsep=0pt,',
            '\t' + 'inner ysep=0pt,',
            '\t' + 'trim left=0pt,',
            '\t' + 'trim right={\\paperw cm},',
            '\t' + ']',
            ]
        return l

    def latex_foot(self):
        """Generate LaTeX code for a tikzpicture foot."""
        return ['\\end{tikzpicture}']


class CV(Page):
    """Define specific CV properties."""

    def __init__(self, layout):
        name = 'Curriculum vitae'
        super().__init__(name, layout)
        self.pages = layout['pages']
        self.box_top = layout['box_top']
        self.box_bottom = layout['box_bottom']
        self.box_left = layout['box_left']
        self.box_right = layout['box_right']
        self.include_photo = layout['include_photo']
        self.title_on_every_page = layout['title_on_every_page']


class Letter(Page):
    """Define specific letter properties."""

    def __init__(self, letter):
        name = 'Letter'
        super().__init__(name, letter)
        self.address_x = letter['address_x']
        self.address_y = letter['address_y']
        self.address_width = letter['address_width']
        self.address_height = letter['address_height']
        self.backaddress_y = letter['backaddress_y']
        self.backaddress_sepline_thickness = letter['backaddress_sepline_thickness']
        self.backaddress_sepchar = letter['backaddress_sepchar']
        self.backaddress_fontsize = letter['backaddress_fontsize']
        self.sender_x = letter['sender_x']
        self.sender_y = letter['sender_y']
        self.sender_width = letter['sender_width']
        self.sender_height = letter['sender_height']
        self.subject_y = letter['subject_y']
        self.text_y = letter['text_y']
        self.closing_y_shift = letter['closing_y_shift']
        self.enclosure_y_shift = letter['enclosure_y_shift']
        self.perforation_mark_x = letter['perforation_mark_x']
        self.perforation_mark_y = letter['perforation_mark_y']
        self.perforation_mark_width = letter['perforation_mark_width']
        self.perforation_mark_thickness = letter['perforation_mark_thickness']
        self.folding_mark_1_x = letter['folding_mark_1_x']
        self.folding_mark_1_y = letter['folding_mark_1_y']
        self.folding_mark_1_width = letter['folding_mark_1_width']
        self.folding_mark_1_thickness = letter['folding_mark_1_thickness']
        self.folding_mark_2_x = letter['folding_mark_2_x']
        self.folding_mark_2_y = letter['folding_mark_2_y']
        self.folding_mark_2_width = letter['folding_mark_2_width']
        self.folding_mark_2_thickness = letter['folding_mark_2_thickness']


class Address(object):
    """Define address properties."""

    def __init__(self, dict_address):
        self.street = dict_address['street']
        self.house = dict_address['house']
        self.postal_code = dict_address['postal_code']
        self.city = dict_address['city']
        self.country = dict_address['country']

    def oneline(self):
        """Write address as single-line LaTeX output."""
        return '{} {}, {} {}'.format(self.street, self.house, self.postal_code,
                                     self.city)

    def twoline(self):
        """Write address as two-line LaTeX output."""
        return '{} {}\\\\{} {}'.format(self.street, self.house,
                                       self.postal_code, self.city)


class Backaddress(object):
    """Define backaddress properties."""

    def __init__(self, dict_pers, dict_address):
        self.first_name = dict_pers['first_name']
        self.family_name = dict_pers['family_name']
        self.street = dict_address['street']
        self.house = dict_address['house']
        self.postal_code = dict_address['postal_code']
        self.city = dict_address['city']
        self.country = dict_address['country']

    def oneline(self, space='1.5cm', separator='$\\bullet$'):
        """Write backaddress as single-line LaTeX output."""
        return '{0} {1}\\hspace{{{6}}}{7}\\hspace{{{6}}}{2} {3}\\hspace{{{6}}}{7}\\hspace{{{6}}}{4} {5}'.format(self.first_name, self.family_name, self.street, self.house, self.postal_code, self.city, space, separator)


class Personal(object):
    """Define personal properties."""

    def __init__(self, dict_pers):
        self.birth_date = dict_pers['birth_date']
        self.birth_location_city = dict_pers['birth_location_city']
        self.citizenship = dict_pers['citizenship']
        self.marital_status = dict_pers['marital_status']
        self.children = dict_pers['children']

    def oneline(self, lang):
        """Write personal data as single-line LaTeX output."""
        if lang == 'en':
            about_str = 'Born {} in {}, {}, {}, {} children'.format(self.birth_date, self.birth_location_city, self.citizenship, self.marital_status, self.children)
        if lang == 'de':
            about_str = 'Geboren am {} in {}, {}, {}, {} Kinder'.format(self.birth_date, self.birth_location_city, self.citizenship, self.marital_status, self.children)
        return about_str

    def twoline(self, lang):
        """Write personal data as two-line LaTeX output."""
        if lang == 'en':
            about_str = 'Born {} in {},\\\\{}, {}, {} children'.format(self.birth_date, self.birth_location_city, self.citizenship, self.marital_status, self.children)
        if lang == 'de':
            about_str = 'Geboren am {} in {},\\\\{}, {}, {} Kinder'.format(self.birth_date, self.birth_location_city, self.citizenship, self.marital_status, self.children)
        return about_str


class Signature:
    """Define signature properties."""

    def __init__(self, name, x, y, height, text_above, text_below, filename):
        self.name = name
        self.x = x
        self.y = y
        self.height = height
        self.text_above = text_above
        self.text_below = text_below
        self.filename = filename

    def create(self):
        """Generate LaTeX output for signature."""
        if self.name == '':
            namestr = ''
        else:
            namestr = '({}) '.format(self.name)
        if self.text_above == '':
            above_str = ''
        else:
            above_str = '{}\\\\'.format(self.text_above)
        return '\\node {}[anchor=north west, text width=10cm] at ({}, {}) {{{}\\includegraphics[height={}cm]{{{}}}\\\\{}}};'.format(namestr, self.x, self.y, above_str, self.height, self.filename, self.text_below)


class Document:
    """Define enclosure document."""

    def __init__(self, name, filename):
        self.name = name
        self.filename = filename

    def pagecount(self):
        """Count total page number of PDF document."""
        with open(self.filename, 'rb') as pdf_file:
            pages = PdfFileReader(pdf_file).numPages
        return pages

    def include(self):
        """Generate LaTeX code for including document."""
        l = []
        for page in range(self.pagecount()):
            l.append('\\begin{tikzpicture}')
            l.append('\t\\node [inner sep=0pt] at (current page.center) {{\\includegraphics[page={0}]{{{1}}}}};'.format(page+1, self.filename))
            l.append('\\end{tikzpicture}')
        return l


class Enclosure:
    """Define entire enclosure."""

    def __init__(self, dict_files):
        self.files = dict_files

    def include(self):
        """Generate LaTeX code to include all documents."""
        doc = []
        for key, value in self.files.items():
            doc = doc + Document(key, value).include()
        return doc


class PhotoArea(object):
    """Definition of photo area."""

    def __init__(self, dict_photo):
        self.pos_x = dict_photo['pos_x']
        self.pos_y = dict_photo['pos_y']
        self.anchor = dict_photo['anchor']
        self.width = dict_photo['width']
        self.height = dict_photo['height']
        self.border = dict_photo['border']
        self.border_width = dict_photo['border_width']
        self.border_color = dict_photo['border_color']


class Area(object):
    """General area definition."""

    def __init__(self, dict_area):
        self.title = dict_area['title']
        self.pos_x = dict_area['pos_x']
        self.pos_y = dict_area['pos_y']
        self.anchor = dict_area['anchor']
        self.head_vspace = dict_area['head_vspace']
        self.head_sepline = dict_area['head_sepline']
        self.head_case = dict_area['head_case']
        self.head_font_size = dict_area['head_font_size']
        self.body_vspace = dict_area['body_vspace']
        self.body_indent = dict_area['body_indent']
        self.body_font_size = dict_area['body_font_size']
        self.color = dict_area['color']
        self.length = dict_area['length']
        self.style = dict_area['style']
        self.icon = dict_area['icon']
        self.show_area = dict_area['show_area']
        self.show_icon = dict_area['show_icon']
        self.hyperlinks = dict_area['hyperlinks']
        self.hide_items = dict_area['hide_items']
        if self.head_case == 'upper':
            self.title = self.title.upper()
        elif self.head_case == 'lower':
            self.title = self.title.lower()


class Cell:
    """Definitions for a table cell."""

    def __init__(self, name, xsep, ysep, align, minimum_width, minimum_height,
                 text_width, text_height):
        self.name = name
        self.shape = 'rectangle'
        self.draw = 'none'
        self.inner_xsep = xsep
        self.inner_ysep = ysep
        self.align = align
        self.minimum_width = minimum_width
        self.minimum_height = minimum_height
        self.text_width = text_width
        self.text_height = text_height

    def set_style(self):
        """Assemble TikZ style."""
        return '{}/.style={{{}, draw={}, inner xsep={}pt, inner ysep={}pt, align={}, minimum width={}cm, minimum height={}cm, text width={}cm, text height={}cm}},'.format(self.name, self.shape, self.draw, self.inner_xsep, self.inner_ysep, self.align, self.minimum_width, self.minimum_height, self.text_width, self.text_height)


class Textbox:
    """General textbox definition."""

    def __init__(self, settings, text):
        self.name = settings['name']
        self.anchor = settings['anchor']
        self.x = settings['x']
        self.y = settings['y']
        self.at = settings['at']
        self.inner_xsep = settings['inner_xsep']
        self.inner_ysep = settings['inner_ysep']
        self.font_size = settings['font_size']
        self.case = settings['case']
        self.text_width = settings['text_width']
        self.align = settings['align']
        self.yshift = settings['yshift']
        self.color = settings['color']
        if self.case == 'upper':
            self.text = text.upper()
        elif self.case == 'lower':
            self.text = text.lower()
        else:
            self.text = text
        if self.at == '':
            self.at = '{}, {}'.format(self.x, self.y)

    def create(self):
        """Generate LaTeX code for textbox."""
        return '\\node ({0}) [anchor={1}, inner xsep={2}pt, inner ysep={3}, font=\\{4}, yshift={5}cm, text width={6}cm, align={7}, color={8}] at ({9}) {{{10}}};'.format(self.name, self.anchor, self.inner_xsep, self.inner_ysep, self.font_size, self.yshift, self.text_width, self.align, self.color, self.at, self.text)


class Table:
    """Definitions for a table (matrix of nodes)."""

    def __init__(self, settings, items):
        self.name = settings['name']
        self.anchor = settings['anchor']
        self.x = settings['x']
        self.y = settings['y']
        self.at = settings['at']
        self.font_size = settings['font_size']
        self.column_styles = settings['column_styles']
        self.items = items

    def head(self):
        """Assemble table header using table properties and column styles."""
        if self.at != '':
            at_str = self.at
        else:
            at_str = '{}, {}'.format(self.x, self.y)
        l = [
                '% {}'.format(self.name.upper()),
                '\\matrix ({}) at ({}) ['.format(self.name, at_str),
                '\t' + 'anchor={},'.format(self.anchor),
                '\t' + 'font=\\{},'.format(self.font_size),
                '\t' + 'matrix of nodes,',
                ]
        for i, style in enumerate(self.column_styles):
            l.append('\t' + 'column {}/.style={{nodes={{{}}}}},'.format(i+1,
                                                                        style))
        l.append('\t]{')
        return l

    def add_row(self, entries):
        """Add single row to a table."""
        row = '\t'
        for entry in entries:
            row = row + '\\node {{{}}}; & '.format(entry)
        row = row[:-3]
        row = row + '\\\\'
        return row

    def foot(self):
        """Footer line."""
        return '\t};'

    def assemble(self):
        """Assemble complete table."""
        table = self.head()
        for item in self.items:
            table.append(self.add_row(item))
        table.append(self.foot())
        return table


class Itemize:
    """Define itemize environment."""

    def __init__(self, label, labelsep, leftmargin, topsep, itemindent,
                 itemsep, items):
        self.label = label
        self.labelsep = labelsep
        self.leftmargin = leftmargin
        self.topsep = topsep
        self.itemindent = itemindent
        self.itemsep = itemsep
        self.items = items

    def generate(self):
        """Generate LaTeX code for itemize environment."""
        l = [
                '\\begin{itemize}[',
                '\t' + 'topsep={},'.format(self.topsep),
                '\t' + 'leftmargin={},'.format(self.leftmargin),
                '\t' + 'labelsep={},'.format(self.labelsep),
                '\t' + 'itemindent={},'.format(self.itemindent),
                '\t' + 'itemsep={},'.format(self.itemsep),
                '\t' + 'label={},'.format(self.label),
                '\t' + ']',
                ]
        for item in self.items:
            l.append('\t\\item {}'.format(item))
        l.append('\\end{itemize}')
        l = ''.join(l)
        return l


class Box(object):
    """General box definition."""

    def __init__(self, color, width, height):
        self.color = color
        self.width = width
        self.height = height


class SkillCircles(object):
    """Definition of skill circles."""

    def __init__(self, total, distance, radius, fillcolor, opencolor,
                 linecolor):
        self.total = total
        self.distance = distance
        self.radius = radius
        self.fillcolor = fillcolor
        self.opencolor = opencolor
        if linecolor == '':
            self.linecolor = 'none'
        else:
            self.linecolor = linecolor

    def define(self):
        """Generate settings for skill circles."""
        l = []
        l.append('circfull/.style={{draw={}, fill={}}},'.format(self.linecolor, self.fillcolor))
        l.append('circopen/.style={{draw={}, fill={}}},'.format(self.linecolor, self.opencolor))
        l.append('pics/skillmax/.style={code={')
        l.append('\t\\foreach \\x in {{1, ..., {}}}{{\\filldraw[circfull] ({}*\\x, 0) circle [radius={}cm];}};'.format(self.total, self.distance, self.radius))
        l.append('\t}')
        l.append('},')
        l.append('pics/skillmin/.style={code={')
        l.append('\t\\foreach \\x in {{1, ..., {}}}{{\\filldraw[circopen] ({}*\\x, 0) circle [radius={}cm];}};'.format(self.total, self.distance, self.radius))
        l.append('\t}')
        l.append('},')
        l.append('pics/skill/.style n args={2}{code={')
        l.append('\t\\foreach \\x in {{1, ..., #1}}{{\\filldraw[circfull] ({}*\\x, 0) circle [radius={}cm];}};'.format(self.distance, self.radius))
        l.append('\t\\foreach \\x in {{#2, ..., {}}}{{\\filldraw[circopen] ({}*\\x, 0) circle [radius={}cm];}};'.format(self.total, self.distance, self.radius))
        l.append('\t}')
        l.append('},')
        return l


class SkillLayout(object):
    """Definition of skill layout."""

    def __init__(self, dict_skill_layout):
        self.number = dict_skill_layout['circle_number']
        self.distance = dict_skill_layout['circle_distance']
        self.show_circles = dict_skill_layout['show_circles']


class List:
    """Definition of a list."""

    def __init__(self, items, separator, orientation):
        self.items = items
        self.separator = separator
        self.orientation = orientation

    def generate(self):
        """Generate LaTeX code for row-type or column-type list."""
        if self.orientation == 'row':
            sep = self.separator
        elif self.orientation == 'column':
            # Insert LaTeX linebreak for column
            sep = '{}\\\\'.format(self.separator)
        else:
            print('[error] Unknown list orientation. Choose between \"row\" and  \"column\".')
        # Join items with separators in between
        return sep.join(self.items)
