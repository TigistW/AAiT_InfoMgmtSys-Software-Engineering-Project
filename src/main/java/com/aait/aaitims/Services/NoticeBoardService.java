package com.aait.aaitims.Services;

import java.util.List;
import java.util.Optional;

import com.aait.aaitims.Entity.NoticeBoard;
import com.aait.aaitims.Repositories.NoticeBoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeBoardService {

	@Autowired
	private NoticeBoardRepository noticeboardRepository;
	
	public void saveNoticeBoard(NoticeBoard notice) {
		noticeboardRepository.save(notice);	
	}

	public List<NoticeBoard> getAllActiveNotices() {
		return noticeboardRepository.findAll();
	}

	public Optional<NoticeBoard> getNoticeById(Long id) {
		return noticeboardRepository.findById(id);
	}
	
	public void deleteNoticeById(long id) {
		this.noticeboardRepository.deleteById(id);

	}
}

