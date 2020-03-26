<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Publication extends Model
{
    protected $table = 'news';
    protected $fillable = [
        'tittle', 'content', 'image', 'new_status_id', 'area_id',
    ];

    public function publication_status()
    {
    	return $this->belongsTo('App\PublicationStatus');
    }

    public function area()
    {
    	return $this->belongsTo('App\Area');
    }
}
