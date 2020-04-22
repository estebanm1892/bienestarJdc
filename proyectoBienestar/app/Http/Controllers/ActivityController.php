<?php

namespace App\Http\Controllers;

use App\Activity;
use App\Day;
use App\Area;
use App\Preregistration;
use App\VirtualResource;
use Illuminate\Database\Eloquent\ModelNotFoundException;
use Illuminate\Http\Request;

class ActivityController extends Controller
{


    private $menu_item = 3;
    private $title_page = 'Actividades';

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        if (auth()->user()->user_type_id == 1) {
            $activities     = Activity::orderBy('area_id','ASC')->paginate(10);
            $preregisters   = Preregistration::where('activity_id', $activities)->get();
        } elseif(auth()->user()->user_type_id == 2) {
            $activities     = Activity::where('area_id', auth()->user()->area_id )->orderBy('area_id','ASC')->paginate(10);
            $preregisters   = Preregistration::where('activity_id', $activities)->get();
        }
        
        

        return view('admin.activities.index', compact('activities'))
        ->with('preregisters', $preregisters)
        ->with('title_page', $this->title_page)
        ->with('menu_item', $this->menu_item);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        $days = Day::orderBy('id', 'ASC')->get();
        $areas  = Area::orderby('name', 'ASC')->get();
        $hours = [
                '05:00 AM','06:00 AM','07:00 AM','08:00 AM',
                '09:00 AM','10:00 AM','11:00 AM','12:00 PM',
                '01:00 PM','02:00 PM','03:00 PM','04:00 PM',
                '05:00 PM','06:00 PM','07:00 PM','08:00 PM',
                '09:00 PM','10:00 PM','11:00 PM','12:00 AM'
                ];

        return view('admin.activities.create')
        ->with('days', $days)
        ->with('areas', $areas)
        ->with('hours', $hours)
        ->with('title_page', $this->title_page)
        ->with('menu_item', $this->menu_item);

    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $rules = [
            'name'          =>  'required|min:4',
            'description'   =>  'required|max:255',
            'initial_hour'  =>  'required',
            'final_hour'    =>  'required',
            'area_id'       =>  'required|numeric'
        ];

        $this->validate($request, $rules);

        $activity = new Activity();

        $activity->name         =   $request->input('name');
        $activity->description  =   $request->input('description');
        $activity->initial_hour =   $request->input('initial_hour');
        $activity->final_hour   =   $request->input('final_hour');
        $activity->area_id      =   $request->input('area_id');

        $activity->save();

        $activity->days()->attach($request->days);

        return redirect()->route('actividades.index')->with('session_msg', 'Se ha creado la actividad correctamente.');
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    public function show_activities_mobile($id)
    {
        try {
            $area = Area::findOrfail($id);
        } catch (ModelNotFoundException $e) {
            return response()->json([
                'error'     =>  'No existe un area para el area con Id: '.$id,
                'success'   =>  false,
            ], 404);
        }     

        $activities = Activity::where('area_id', $area->id);

        $area = $activities->orderby('id', 'DESC')
        ->with(['days' => function($days){
                $days->select([
                    'name'
                ]);
            }])
            ->select([
                'id',
                'name',
                'description',
                'initial_hour',
                'final_hour',
                'area_id'
            ])
            ->get();
             
        return response()->json($area, 200)->setEncodingOptions(JSON_NUMERIC_CHECK);

    }


    

    public function show_preregister($id)
    {
        $activity       =   Activity::findOrFail($id);
        $preregisters   =   Preregistration::where('activity_id', $activity->id)->orderBy('id', 'DESC')->paginate(10);        

        Preregistration::where('activity_id', $activity->id)->where('readed', false)->update(['readed' => true]);

        return view('admin.preregisters.index', compact('activity'))
        ->with('preregisters', $preregisters)
        ->with('title_page', $this->title_page)
        ->with('menu_item', $this->menu_item);

    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        $activity   =   Activity::findOrFail($id);
        $areas      =   Area::orderby('name', 'ASC')->get();
        $myArea     =   Area::find($activity->area_id);
        $hours  = [
                '05:00 AM','06:00 AM','07:00 AM','08:00 AM',
                '09:00 AM','10:00 AM','11:00 AM','12:00 PM',
                '01:00 PM','02:00 PM','03:00 PM','04:00 PM',
                '05:00 PM','06:00 PM','07:00 PM','08:00 PM',
                '09:00 PM','10:00 PM','11:00 PM','12:00 AM'
                ];
        $myInitial  =   $activity->initial_hour;
        $days       =   Day::orderby('id', 'ASC')->get();
        $myDays     =   $activity->days->pluck('id')->toArray();
        $resources  =   VirtualResource::where('activity_id', $activity->id)->get();

        return view('admin.activities.edit', compact('activity'))
        ->with('areas', $areas)
        ->with('myArea', $myArea)
        ->with('hours', $hours)
        ->with('myInitial', $myInitial)
        ->with('days', $days)
        ->with('myDays', $myDays)
        ->with('resources', $resources)
        ->with('title_page', $this->title_page)
        ->with('menu_item', $this->menu_item);

    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $rules = [
            'name'          =>  'required|min:4',
            'description'   =>  'required|max:255',
            'initial_hour'  =>  'required',
            'final_hour'    =>  'required',
            'area_id'       =>  'required|numeric'
        ];

        $this->validate($request, $rules);

        $activity = Activity::find($id);

        $activity->name         =   $request->input('name');
        $activity->description  =   $request->input('description');
        $activity->initial_hour =   $request->input('initial_hour');
        $activity->final_hour   =   $request->input('final_hour');
        $activity->area_id      =   $request->input('area_id');

        $activity->save();

        $activity->days()->detach();        
        $activity->days()->attach($request->days);

        return redirect()->route('actividades.index')->with('session_msg', 'Se ha editado la actividad correctamente.');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $activity = Activity::findOrFail($id);

        $vresources = VirtualResource::where('activity_id', $activity->id)->get();

        foreach ($vresources as $vresource) {
            
            if ($vresource->image != null) {
                if(file_exists(public_path().str_replace(env('APP_URL'), '/', $vresource->image))){
                    unlink(public_path().str_replace(env('APP_URL'), '/', $vresource->image));
                }
            }

            if ($vresource->docs != null) {
                if(file_exists(public_path().str_replace(env('APP_URL'), '/', $vresource->docs))){
                    unlink(public_path().str_replace(env('APP_URL'), '/', $vresource->docs));
                }
            }       

        }

        $activity->delete();

        return redirect()->route('actividades.index')->with('session_msg', 'Se ha eliminado correctamente la actividad.');
    }
}
