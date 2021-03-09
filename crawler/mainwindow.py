from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtCore import Qt
from PyQt5 import uic
from crawl import Head
import json

def find_first_empty_row(table, col):
    r = 0
    f = False
    while r < table.rowCount():
        if table.item(r, col) is None:
            return r
        r += 1
    return 0

class MainWindow(QtWidgets.QMainWindow, uic.loadUiType("main.ui")[0]):
    def __init__(self, parent=None):
        self.parent = parent
        QtWidgets.QMainWindow.__init__(self, self.parent)
        self.setupUi(self)
        self.btn_get_head.clicked.connect(self.url)
        self.head_name.textEdited.connect(lambda: self.onchange("NAME"))
        self.head_string.textEdited.connect(lambda: self.onchange("B64"))
        self.btn_clear.clicked.connect(self.clear)
        header = self.ingre_table.horizontalHeader()
        header.setSectionResizeMode(0, QtWidgets.QHeaderView.Stretch)
        header.setSectionResizeMode(1, QtWidgets.QHeaderView.ResizeToContents)
        self.btn_ingre.clicked.connect(self.ingre)
        self.ingre_selected = False
        self.mat_name.textEdited.connect(lambda: self.set_ingre_sel(False))
        self.mat_amount.valueChanged.connect(lambda: self.set_ingre_sel(False))
        self.ingre_table.itemClicked.connect(lambda i: self.set_ingre_sel(True, i))
        self.btn_close.clicked.connect(self.close)
        self.btn_file.clicked.connect(self.select_file)
        self.btn_add_file.clicked.connect(self.append_file)
        self.selected_mat = ""
        self.clear()
        self.out_file = ""
    
    def select_file(self):
        dialog = QtWidgets.QFileDialog()
        if dialog.exec_():
            self.out_file = dialog.selectedFiles()[0]
            self.btn_file.setText("File Selected")
            self.btn_add_file.setEnabled(True)
    
    def append_file(self):
        with open(self.out_file, "r") as f:
            file_json = json.loads(f.read())
        file_json.append(self.head.to_dict())
        with open(self.out_file, "w") as f:
            f.write(json.dumps(file_json, indent=2))
        
    def clear(self):
        self.head_name.setText("")
        self.head_string.setText("")
        self.head = Head()
        self.json_text.setText(self.head.to_json())
        self.renew_table()
        
    def set_ingre_sel(self, b, i=None):
        self.ingre_selected = b
        if not b:
            self.btn_ingre.setText("Add")
        else:
            self.btn_ingre.setText("Remove")
            self.selected_mat = self.ingre_table.item(i.row(), 0).text()
    
    def ingre(self):
        if not self.ingre_selected:
            mat = self.mat_name.text()
            am = self.mat_amount.value()
            if mat != "":
                mat = mat.upper().replace(" ", "_")
                self.head.add_ingredient(mat, am)
                r = self.ingre_table.rowCount()
                self.ingre_table.setRowCount(r + 1)
                self.ingre_table.setItem(r, 0, QtWidgets.QTableWidgetItem(mat))
                self.ingre_table.setItem(r, 1, QtWidgets.QTableWidgetItem(str(am)))
                self.renew_json()
                self.mat_name.setText("")
                self.mat_amount.setValue(1)
        else:
            self.head.remove_ingredient(self.selected_mat)
            self.renew_table()
            self.renew_json()
    
    def renew_table(self):
        self.ingre_table.clear()
        self.ingre_table.setRowCount(0)
        header = self.ingre_table.horizontalHeader()
        header.setSectionResizeMode(0, QtWidgets.QHeaderView.Stretch)
        header.setSectionResizeMode(1, QtWidgets.QHeaderView.ResizeToContents)
        self.ingre_table.setHorizontalHeaderLabels(["Material", "Amount"])
        for row, mat in enumerate(self.head.ingredients.keys()):
            self.ingre_table.setRowCount(row + 1)
            self.ingre_table.setItem(row, 0, QtWidgets.QTableWidgetItem(mat))
            self.ingre_table.setItem(row, 1, QtWidgets.QTableWidgetItem(self.head.ingredients[mat]))
    
    def url(self):
        self.head.from_url(self.line_head_url.text())
        self.line_head_url.setText("")
        self.renew_json()
        self.head_name.setText(self.head.name)
        self.head_string.setText(self.head.b64)
    
    def onchange(self, f):
        if f == "NAME":
            self.head.name = self.head_name.text()
        elif f == "B64":
            self.head.b64 = self.head_string.text()
        self.renew_json()
    
    def renew_json(self):
        self.json_text.setText(self.head.to_json())
