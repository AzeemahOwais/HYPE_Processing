HDrawablePool pool;
HColorField colorField;

int numAssets = 576;
HProximity aProx;
PVector[] gridPos;
float[] aMin;
float[] aMax;

int cellSize = 25;

void setup() {
  size(640,640);
  H.init(this).background(#202020);
  smooth();

  gridPos = new PVector[numAssets];
  aMin = new float[numAssets];
  aMax = new float[numAssets];

  colorField = new HColorField(width, height)
      .addPoint(0, height/2, #FF0066, 0.5f)
      .addPoint(width, height/2, #3300FF, 0.5f)
      .fillOnly()
    ;

  pool = new HDrawablePool(numAssets);
  pool.autoAddToStage()
    .add ( new HRect(cellSize) )
    .layout (
      new HGridLayout()
      .startX(20)
      .startY(20)
      .spacing(cellSize+1,cellSize+1)
      .cols(24)
    )
    .setOnCreate (
        new HCallback() {
          public void run(Object obj) {
            int i = pool.currentIndex();

            HDrawable d = (HDrawable) obj;
            d.fill( #000000, 10 ).anchorAt(H.CENTER);

            gridPos[i] = new PVector(d.x(), d.y());

            aMin[i] = 50;
            aMax[i] = 255;

            colorField.applyColor(d);
        }
      }
    )
    .requestAll();

  // spring, ease, min(ArrayList), max(ArrayList), radius
  aProx = new HProximity(0.9, 0.3, aMin, aMax, 100);
}

void draw() {
  HIterator<HDrawable> it = pool.iterator();
  int i = 0;
  while(it.hasNext()) {
    HDrawable d = it.next();
    d.fill( #000000, int(aProx.run(i)) );
    colorField.applyColor(d);
    i++;
  }

  H.drawStage();
}


