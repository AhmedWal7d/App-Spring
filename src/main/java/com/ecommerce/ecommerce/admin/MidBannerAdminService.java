package com.ecommerce.ecommerce.admin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.admin.dto.MidBannerUpsertRequest;
import com.ecommerce.ecommerce.home.MaxActiveMidBannersException;
import com.ecommerce.ecommerce.home.entity.MidBanner;
import com.ecommerce.ecommerce.home.repository.MidBannerRepository;

@Service
public class MidBannerAdminService {

	private final MidBannerRepository midBannerRepository;

	public MidBannerAdminService(MidBannerRepository midBannerRepository) {
		this.midBannerRepository = midBannerRepository;
	}

	@Transactional
	public MidBanner create(MidBannerUpsertRequest r) {
		assertActiveLimit(Boolean.TRUE.equals(r.active()), null);
		MidBanner b = new MidBanner();
		apply(b, r);
		return midBannerRepository.save(b);
	}

	@Transactional
	public MidBanner update(long id, MidBannerUpsertRequest r) {
		MidBanner b = midBannerRepository.findById(id).orElseThrow(ContentNotFoundException::new);
		assertActiveLimit(Boolean.TRUE.equals(r.active()), id);
		apply(b, r);
		return midBannerRepository.save(b);
	}

	@Transactional
	public void delete(long id) {
		if (!midBannerRepository.existsById(id)) {
			throw new ContentNotFoundException();
		}
		midBannerRepository.deleteById(id);
	}

	private void assertActiveLimit(boolean active, Long excludeId) {
		if (!active) {
			return;
		}
		long countOthers = excludeId == null
				? midBannerRepository.countByActiveTrue()
				: midBannerRepository.countByActiveTrueAndIdNot(excludeId);
		if (countOthers >= 2) {
			throw new MaxActiveMidBannersException();
		}
	}

	private static void apply(MidBanner b, MidBannerUpsertRequest r) {
		b.setImageUrl(r.imageUrl().trim());
		b.setTitleAr(trimToNull(r.titleAr()));
		b.setTitleEn(trimToNull(r.titleEn()));
		b.setLinkHref(trimToNull(r.linkHref()));
		b.setSortOrder(r.sortOrder());
		b.setActive(Boolean.TRUE.equals(r.active()));
	}

	private static String trimToNull(String s) {
		if (s == null) {
			return null;
		}
		String t = s.trim();
		return t.isEmpty() ? null : t;
	}
}
