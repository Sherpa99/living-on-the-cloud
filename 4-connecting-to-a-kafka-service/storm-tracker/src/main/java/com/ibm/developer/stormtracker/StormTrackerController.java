package com.ibm.developer.stormtracker;

import java.net.URI;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/storms")
public class StormTrackerController {

	private StormRepo repo;
	
	
	public StormTrackerController(StormRepo repo) {
		this.repo = repo;
	}

	@GetMapping
	public ResponseEntity<Iterable<Storm>> findAllStorms() {
		return ResponseEntity.ok(repo.findAll());
	}
	
	@GetMapping("/{stormId}")
	public ResponseEntity<Storm> findById(@PathVariable long stormId) {
		return ResponseEntity.ok(repo.findById(stormId).get());
	}

	@PostMapping
	public ResponseEntity<?> addNewStorm(@RequestBody Storm storm) {
		storm = repo.save(storm);
		return ResponseEntity.created(URI.create("/api/v1/storms/" + storm.getId())).build();
	}
}
