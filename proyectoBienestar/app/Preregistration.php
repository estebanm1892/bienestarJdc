<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Preregistration extends Model
{
    protected $table = 'preregistrations';
    protected $fillable = [
        'document', 'name', 'email', 'semester', 'activity_id', 'academic_program_id', 'readed',
    ];

    public function activities()
    {
    	return $this->belongsTo('App\activity');
    }

    public function academic_program()
    {
    	return $this->BelongsTo('App\AcademicProgram');
    }
}
