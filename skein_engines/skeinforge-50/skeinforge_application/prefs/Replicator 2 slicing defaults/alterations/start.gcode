(**** beginning of start.gcode ****)
<<<<<<< HEAD
(This is start code for The Replicator running a single material print)
=======
(This is start code for The Replicator 2 running a single material print)
>>>>>>> jetty/master
M73 P0 (enable build progress)
M103 (disable RPM)
G21 (set units to mm)
G90 (set positioning to absolute)
G10 P1 X16.5 Y0 Z-0.3 (Designate T0 Offset)
G10 P2 X-16.5 Y0 Z-0.3 (Designate T1 Offset)
G55 (Recall offset cooridinate system for T1)
M104 S220 T0 (set extruder temperature)
<<<<<<< HEAD
M109 S100 T0 (set HBP temperature)
(**** begin homing ****)
G162 X Y F2500 (home XY axes maximum
=======
(**** begin homing ****)
G162 X Y F2500 (home XY axes maximum)
>>>>>>> jetty/master
G161 Z F1100 (home Z axis minimum)
G92 Z-5 (set Z to -5)
G1 Z0.0 (move Z to "0")
G161 Z F100 (home Z axis minimum)
M132 X Y Z A B (Recall stored home offsets for XYZAB axis)
(**** end homing ****)
G1 X105 Y-70 Z10 F3300.0 (move to waiting position)
G130 X0 Y0 A0 B0 (Set Stepper motor Vref to lower value while heating)
M6 T0 (wait for toolhead parts, nozzle, HBP, etc., to reach temperature)
G130 X127 Y127 A127 B127 (Set Stepper motor Vref to defaults)
M6 T0
M108 T0
G0 X105 Y-70 (Position Nozzle)
G0 Z0.6     (Position Height)
G92 E0 (Set E to 0)
G1 E4 F300 (Extrude 4mm of filament)
G92 E0 (Set E to 0 again)
(**** end of start.gcode ****)
