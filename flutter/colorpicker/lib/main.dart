import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        backgroundColor: Color(0xFF424242), // Kolor tła: cardview_dark_background
        body: ColorPicker(),
      ),
    );
  }
}

class ColorPicker extends StatefulWidget {
  @override
  _ColorPickerState createState() => _ColorPickerState();
}

class _ColorPickerState extends State<ColorPicker> {
  late TextEditingController _redController;
  late TextEditingController _greenController;
  late TextEditingController _blueController;
  late Color _currentColor;

  @override
  void initState() {
    super.initState();
    _redController = TextEditingController(text: '0');
    _greenController = TextEditingController(text: '0');
    _blueController = TextEditingController(text: '0');
    _currentColor = Colors.black;
  }

  @override
  void dispose() {
    _redController.dispose();
    _greenController.dispose();
    _blueController.dispose();
    super.dispose();
  }

  void _updateColor() {
    int redValue = _redController.text.isNotEmpty ? int.parse(_redController.text) : 0;
    int greenValue = _greenController.text.isNotEmpty ? int.parse(_greenController.text) : 0;
    int blueValue = _blueController.text.isNotEmpty ? int.parse(_blueController.text) : 0;

    setState(() {
      _currentColor = Color.fromRGBO(
        redValue,
        greenValue,
        blueValue,
        1.0,
      );
    });
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Padding(
        padding: EdgeInsets.only(top: 90.0),
        child: Center(
          child: Column(
            children: [
              SizedBox(height: 20.0),
              Padding(
                padding: EdgeInsets.only(top: 20.0),
                child: SizedBox(
                  width: 250.0,
                  height: 250.0,
                  child: DecoratedBox(
                    decoration: BoxDecoration(
                      color: _currentColor,
                      borderRadius: BorderRadius.circular(20.0),
                    ),
                  ),
                ),
              ),
              SizedBox(height: 70.0),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  ColorInputField('R', _redController, Colors.red, _updateColor),
                  ColorInputField('G', _greenController, Colors.green, _updateColor),
                  ColorInputField('B', _blueController, Colors.blue, _updateColor),
                ],
              ),
              SizedBox(height: 100.0), // Przycisk przesunięty o 100 pikseli w dół
              ElevatedButton(
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => SecondScreen()),
                  );
                },
                child: Icon(Icons.arrow_forward),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class ColorInputField extends StatefulWidget {
  final String label;
  final TextEditingController controller;
  final Color fieldColor;
  final VoidCallback onUpdate;

  ColorInputField(this.label, this.controller, this.fieldColor, this.onUpdate);

  @override
  _ColorInputFieldState createState() => _ColorInputFieldState();
}

class _ColorInputFieldState extends State<ColorInputField> {
  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 62.0,
      child: Column(
        children: [
          SizedBox(height: 8.0),
          Text(
            widget.label,
            style: TextStyle(fontSize: 12.0, color: Colors.white),
          ),
          SizedBox(height: 4.0),
          Container(
            width: 62.0,
            height: 21.0,
            child: TextField(
              controller: widget.controller,
              onChanged: (value) {
                widget.onUpdate();
              },
              textAlign: TextAlign.center,
              keyboardType: TextInputType.number,
              decoration: InputDecoration(
                contentPadding: EdgeInsets.symmetric(horizontal: 8.0, vertical: 4.0),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(20.0),
                ),
                filled: true,
                fillColor: widget.fieldColor,
              ),
              style: TextStyle(fontSize: 12.0),
            ),
          ),
        ],
      ),
    );
  }
}

class SecondScreen extends StatefulWidget {
  @override
  _SecondScreenState createState() => _SecondScreenState();
}

class _SecondScreenState extends State<SecondScreen> {
  List<int> percentages = [0, 0, 0, 0, 0];
  List<Color> colors = [
    Colors.red,
    Colors.blue,
    Colors.yellow,
    Colors.green,
    Colors.purple,
  ];

  List<String> colorNames = [
    'Red',
    'Blue',
    'Yellow',
    'Green',
    'Purple',
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xFF424242),
      body: SingleChildScrollView(
        child: Container(
          padding: EdgeInsets.symmetric(vertical: 16.0),
          child: Center(
            child: Column(
              children: [
                SizedBox(height: 20.0),
                SizedBox(
                  width: 250.0,
                  height: 250.0,
                  child: DecoratedBox(
                    decoration: BoxDecoration(
                      color: _currentColor(),
                      borderRadius: BorderRadius.circular(20.0),
                    ),
                  ),
                ),
                SizedBox(height: 20.0),
                Column(
                  children: [
                    for (int i = 0; i < 5; i++)
                      Column(
                        children: [
                          _colorRectangle(colors[i], i),
                          SizedBox(height: 20.0),
                        ],
                      ),
                  ],
                ),
                SizedBox(height: 20.0),
                ElevatedButton(
                  onPressed: () {
                    Navigator.pop(context);
                  },
                  child: Icon(Icons.arrow_back),
                ),
                SizedBox(height: 50.0),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Color _currentColor() {
    double red = 0, green = 0, blue = 0;

    for (int i = 0; i < colors.length; i++) {
      double percent = percentages[i] / 100;

      red += colors[i].red * percent;
      green += colors[i].green * percent;
      blue += colors[i].blue * percent;
    }

    return Color.fromRGBO(red.toInt(), green.toInt(), blue.toInt(), 1.0);
  }

  Widget _colorRectangle(Color color, int index) {
    return SizedBox(
      width: 248,
      height: 62,
      child: Row(
        children: [
          Expanded(
            child: DecoratedBox(
              decoration: BoxDecoration(
                color: color,
                borderRadius: BorderRadius.circular(20.0),
              ),
              child: Padding(
                padding: const EdgeInsets.symmetric(horizontal: 8.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text(
                      '${colorNames[index]} - ${percentages[index]}% ',
                      style: TextStyle(color: Colors.white),
                    ),
                    _buildButtons(index),
                  ],
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildButtons(int index) {
    return Row(
      children: [
        IconButton(
          onPressed: () {
            if (percentages[index] < 100) {
              setState(() {
                percentages[index] += 10;
              });
            }
          },
          icon: Icon(Icons.add),
        ),
        IconButton(
          onPressed: () {
            if (percentages[index] > 0) {
              setState(() {
                percentages[index] -= 10;
              });
            }
          },
          icon: Icon(Icons.remove),
        ),
      ],
    );
  }
}